package com.ohmuk.folitics.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.beans.CommentBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Chart;
import com.ohmuk.folitics.hibernate.entity.comment.ChartComment;
import com.ohmuk.folitics.hibernate.entity.comment.ChartCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.ChartCommentCountId;
import com.ohmuk.folitics.hibernate.repository.comment.IChartCommentRepository;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class ChartCommentService implements ICommentService<ChartComment> {

	private static Logger logger = LoggerFactory
			.getLogger(ChartCommentService.class);

	@Autowired
	private ICommentCountService<ChartCommentCount> chartCommentCountService;

	@Autowired
	private IChartCommentRepository chartCommentRepository;

	@Override
	public ChartComment create(CommentBean commentBean)
			throws MessageException, Exception {

		logger.debug("Inside create  ChartComment Service");

		if (commentBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId found to be null"));
		}

		if (commentBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId found to be null"));
		}

		// create ChartComment object from comment bean
		ChartComment chartComment = new ChartComment();
		chartComment.setUserId(commentBean.getUserId());
		chartComment.setComponentId(commentBean.getComponentId());
		chartComment.setComponentType(commentBean.getComponentType());
		chartComment.setComment(commentBean.getComment());

		chartCommentRepository.save(chartComment);

		// add monetization points to user for Comment on any component
		chartCommentRepository.addMonetizationPoints(commentBean, "Comment");

		// Adding counter for the Comment
		ChartCommentCount chartCommentCount = new ChartCommentCount();
		Chart chart = new Chart();
		chart.setId(commentBean.getComponentId());

		ChartCommentCountId chartCommentCountId = new ChartCommentCountId();
		chartCommentCountId.setChart(chart);

		chartCommentCount.setId(chartCommentCountId);

		ChartCommentCount chartCommentCount2 = chartCommentCountService
				.getByComponentId(commentBean.getComponentId());

		// if count avaialble for component adding counter else entering counter
		// for the first time for user and component

		if (chartCommentCount2 != null) {
			chartCommentCount2.setCommentCount(chartCommentCount2
					.getCommentCount() + 1);
			chartCommentCountService.create(chartCommentCount2);
		} else {
			chartCommentCount.setCommentCount(1l);
			chartCommentCountService.create(chartCommentCount);
		}

		logger.debug("Exiting create Comment");
		return chartComment;

	}

	@Override
	public ChartComment read(Long id) throws MessageException {
		logger.info("Entered ChartComment service read method");
		if (id == null) {
			logger.error("Id found to be null");
			throw new MessageException("Id found to be null");
		}

		ChartComment chartComment = chartCommentRepository.find(id);

		logger.info("Exiting ChartComment service read method");
		return chartComment;
	}

	@Override
	public List<ChartComment> readAll() {
		logger.info("Entered ChartComment service readAll method");
		logger.debug("Getting all ChartComment");
		return chartCommentRepository.findAll();
	}

	@Override
	public ChartComment update(CommentBean commentBean) throws MessageException {

		logger.debug("Inside Update Comment method");

		ChartComment originalData = chartCommentRepository.find(commentBean
				.getId());

		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException(
					"No Record found in database for update"));
		}
		originalData.setEditedTime(DateUtils.getSqlTimeStamp());

		ChartComment chartComment = chartCommentRepository.save(originalData);

		if (chartComment == null) {
			logger.error("Something went wrong, record not updated");
			throw (new MessageException(
					"Something went wrong, record not updated"));
		}

		logger.debug("Updated comment  : " + chartComment);
		logger.debug("Exiting Update");

		return chartComment;
	}

	@Override
	public ChartComment delete(Long id) throws MessageException, Exception {

		logger.info("Inside hard delete comment by ID");

		if (id == null) {
			logger.error("Comment ID found to be null");
			throw (new MessageException("Comment ID can't be null"));
		}

		ChartComment chartComment = chartCommentRepository.find(id);

		if (chartComment == null) {
			logger.error("Something went wrong, record not found for given id: "
					+ id + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: "
							+ id + ", Can't delete record."));
		}

		ChartCommentCount chartCommentCount = chartCommentCountService
				.getByComponentId(id);

		if (chartCommentCount != null) {

			chartCommentCountService.delete(chartCommentCount);

		}

		chartCommentRepository.delete(id);

		ChartComment cComment = chartCommentRepository.find(id);

		logger.debug("Deleted Comment :" + chartComment);
		logger.debug("Exiting  delete comment by ID");

		return cComment;
	}

	@Override
	public List<ChartComment> getByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException {
		logger.debug("Entered getByComponentIdAndUserId Comment");

		List<ChartComment> chartComments = null;
		chartComments = chartCommentRepository.getByComponentIdAndUserId(
				componentId, userId);

		logger.debug("Comment fetched: " + chartComments.size());
		logger.debug("Exiting getByComponentIdAndUserId comment");

		return chartComments;
	}

	@Override
	public List<ChartComment> getAllCommentsForComponent(Long componentId)
			throws MessageException {

		logger.debug("Entered getAllCommentsForComponent method");

		List<ChartComment> chartComments = null;
		chartComments = chartCommentRepository
				.getAllCommentsForComponent(componentId);

		logger.debug("Comment fetched: " + chartComments.size());
		logger.debug("Exiting getAllCommentsForComponent method");

		return chartComments;
	}

	@Override
	public List<ChartComment> getAllCommentsForUserId(Long userId)
			throws MessageException {
		logger.debug("Entered getAllCommentsForUserId method");

		List<ChartComment> chartComments = null;
		chartComments = chartCommentRepository.getAllCommentsForUserId(userId);

		logger.debug("Comment fetched: " + chartComments.size());
		logger.debug("Exiting getAllCommentsForUserId method");

		return chartComments;
	}

}
