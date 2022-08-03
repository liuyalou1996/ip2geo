package com.universe.ip2geo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author 刘亚楼
 * @date 2022/7/31
 */
@Slf4j
public abstract class AbstractImportResourceService {

	@Autowired
	private ThreadPoolTaskExecutor executor;

	protected <T> void doImport(Supplier<List<T>> supplier, Consumer<List<T>> consumer, int sizePerBatch, int sizePerSubBatch) {
		List<T> data = supplier.get();
		ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
		// 每个批次处理1万条
		int size = data.size();
		int totalBatch = size % sizePerBatch == 0 ? size / sizePerBatch : (size / sizePerBatch + 1);
		// 300个线程，每个线程处理1万条
		for (int batchCount = 0; batchCount < totalBatch; batchCount++) {
			log.info("共{}主批次，正在处理第{}主批次", totalBatch, batchCount + 1);
			threadLocal.set(batchCount + 1);

			int fromIndex = batchCount * sizePerBatch;
			int toIndex = Optional.of((batchCount + 1) * sizePerBatch).map(index -> index > size ? size : index).get();
			executor.execute(() -> {
				List<T> subBatch = data.subList(fromIndex, toIndex);
				int subSize = subBatch.size();
				int subTotalBatch = subSize % sizePerSubBatch == 0 ? subSize / sizePerSubBatch : (subSize / sizePerSubBatch + 1);
				for (int subBatchCount = 0; subBatchCount < subTotalBatch; subBatchCount++) {
					log.info("主批次号：{}，共{}子批次，正在处理第{}子批次", threadLocal.get(), subTotalBatch, (subBatchCount + 1));
					int subFromIndex = fromIndex + subBatchCount * sizePerSubBatch;
					int subToIndex = Optional.of(fromIndex + (subBatchCount + 1) * sizePerSubBatch).map(index -> index > size ? size : index).get();
					try {
						consumer.accept(data.subList(subFromIndex, subToIndex));
					} catch (Exception e) {
						log.error(e.getMessage(), e);
						log.error("处理失败，主批次号：{}，子批次号：{}, fromIndex：{}，toIndex：{}", threadLocal.get(), (subBatchCount + 1), subFromIndex, subToIndex);
					}
				}
			});
		}
	}
}
