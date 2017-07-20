package com.ohmuk.folitics.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ohmuk.folitics.businessDelegate.interfaces.IOpinionBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.enums.AttachmentType;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.enums.FileType;
import com.ohmuk.folitics.enums.OpinionState;
import com.ohmuk.folitics.enums.SentimentState;
import com.ohmuk.folitics.enums.SentimentType;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.attachment.AttachmentFile;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.util.DateUtils;

/**
 * @author Abhishek
 *
 */
@Controller
@RequestMapping("/opinion")
public class OpinionController {
    final static Logger logger = Logger.getLogger(OpinionController.class);
    @Autowired
    private IOpinionBusinessDelegate businessDelegate;

    @RequestMapping
    public String getOpinionPage() {
        return "opinion-page";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Opinion> getAdd() {
        List<Opinion> opinions = new ArrayList<>();
        opinions.add(getTestOpinion());
        return new ResponseDto<Opinion>(true, opinions);
    }

    /**
     * Spring web service(POST) to add a opinion
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Opinion opinion
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Opinion>(Boolean , List<Opinion>)
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<Opinion> add(@RequestBody Opinion opinion) {
        try {
            opinion = businessDelegate.create(opinion);
        } catch (Exception exception) {
            logger.debug("Error while saving opinion with opinion type = " + opinion.getType() + ", sentiment id = "
                    + opinion.getSentiment().getId() + " and user id = " + opinion.getUser().getId());
            logger.error("Exception in adding Opinion");
            logger.error("Exception: "+exception);
            return new ResponseDto<Opinion>(false);
        }
        if (opinion != null) {
            return new ResponseDto<Opinion>(true, opinion);
        }
        return new ResponseDto<Opinion>(false);
    }

    /**
     * Spring web service(POST) to add a opinion with multipart request
     * 
     * @author Abhishek
     * @param Multipart file
     * @param com.ohmuk.folitics.jpa.entity.Opinion opinion
     * @return com.ohmuk.folitics.dto.ResponseDto.ResponseDto<Opinion>(Boolean , List<Opinion>)
     * @throws Exception
     */
    @RequestMapping(value = "/addOpinion", method = RequestMethod.POST)
    public @ResponseBody ResponseDto<Opinion> add(@RequestPart(value = "file") MultipartFile file,
            @Validated @RequestPart(value = "opinion") Opinion opinion) throws Exception {
        if (file != null) {
            String[] contentType = file.getContentType().split("/");

            opinion.getAttachment().setAttachmentType(AttachmentType.getAttachmentType(contentType[0]).getValue());
            opinion.getAttachment().setFileType(FileType.getFileType(contentType[1]).getValue());

            if (contentType[0].equals(AttachmentType.IMAGE.getValue())) {
                AttachmentFile attachmentFile = new AttachmentFile();
                try {
                    attachmentFile.setData(file.getBytes());
                } catch (IOException exception) {
                    logger.error("Some exception occured while reading file");
                    logger.error("Exception: "+exception);
                    throw new Exception("Some exception occured while reading file", exception.getCause());
                }
                attachmentFile.setType(FileType.getFileType(contentType[1]).getValue());
                opinion.getAttachment().setAttachmentFile(attachmentFile);
            } else {
                // TODO save audio video in disk somewhere
            }
        }
        opinion = businessDelegate.create(opinion);
        if (opinion != null) {
            return new ResponseDto<Opinion>(true, opinion);
        }
        return new ResponseDto<Opinion>(false);
    }

    /**
     * Spring web service(POST) to edit a opinion
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.Opinion opinion
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Opinion> edit(@Validated @RequestBody Opinion opinion) {
        try {
            opinion = businessDelegate.update(opinion);
        } catch (Exception exception) {
            logger.error("Exception in editing Opinion");
            logger.error("Exception: "+exception);
            return new ResponseDto<Opinion>(false);
        }
        if (opinion != null) {
            return new ResponseDto<Opinion>(true, opinion);
        }
        return new ResponseDto<Opinion>(false);
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Opinion> delete(Long id) {
        Opinion opinion = null;
        try {
            opinion = businessDelegate.delete(id);
        } catch (Exception exception) {
            logger.error("Exception in delete by id Opinion");
            logger.error("Exception: "+exception);
            return new ResponseDto<Opinion>(false);
        }
        if (opinion != null) {
            if (opinion.getState().equals(ComponentState.DELETED.getValue())) {
                return new ResponseDto<Opinion>(true);
            }
        }
        return new ResponseDto<Opinion>(false);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Opinion> delete(@Validated @RequestBody Opinion opinion) {
        try {
            opinion = businessDelegate.delete(opinion);
        } catch (Exception exception) {
            logger.error("Exception in delete Opinion");
            logger.error("Exception: "+exception);
            return new ResponseDto<Opinion>(false);
        }
        if (opinion != null) {
            if (opinion.getState().equals(ComponentState.DELETED.getValue())) {
                return new ResponseDto<Opinion>(true);
            }
        }
        return new ResponseDto<Opinion>(false);
    }

    @RequestMapping(value = "/deleteFromDbById", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Opinion> deleteFromDB(Long id) {
        try {
            if (businessDelegate.deleteFromDB(id)) {
                return new ResponseDto<Opinion>(true);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ResponseDto<Opinion>(false);
    }

    @RequestMapping(value = "/deleteFromDb", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<Opinion> deleteFromDB(@Validated @RequestBody Opinion opinion) {
        try {
            if (businessDelegate.deleteFromDB(opinion)) {
                return new ResponseDto<Opinion>(true);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ResponseDto<Opinion>(false);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Opinion> getall() {
        List<Opinion> opinions = null;
        try {
            opinions = businessDelegate.readAll();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (opinions != null) {
            return new ResponseDto<Opinion>(true, opinions);
        }
        return new ResponseDto<Opinion>(false);
    }
    
    @RequestMapping(value = "/getTopMostOpinion", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Opinion> getTopMostOpinion(Long sentimentId) {
        
        return null;
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<Opinion> find(Long id) {
        Opinion opinion = null;
        try {
            opinion = businessDelegate.read(id);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (opinion != null) {
            return new ResponseDto<Opinion>(true, opinion);
        }

        return new ResponseDto<Opinion>(false);
    }

    @RequestMapping(value = "/getTestOpinion", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Opinion getTestOpinion() {
        return getDummyOpinion();
    }

    private Opinion getDummyOpinion() {
        Opinion opinion = new Opinion();

        Sentiment sentiment = new Sentiment();
        sentiment.setId(1l);
        sentiment.setCreateTime(DateUtils.getSqlTimeStamp());
        sentiment.setSubject("Subject for test Sentiment");
        sentiment.setDescription("Description for test Sentiment");
        sentiment.setCreated_By(new Long("100"));
        sentiment.setState(SentimentState.ALIVE.getValue());
        sentiment.setStartTime(DateUtils.getSqlTimeStamp());
        sentiment.setEndTime(DateUtils.getSqlTimeStamp());
        sentiment.setType(SentimentType.TEMPORARY.toString());
        Set<Sentiment> sentiments = new HashSet<Sentiment>();
        sentiments.add(sentiment);

        OpinionState os = OpinionState.NEW;
        opinion.setSubject("Subject of Opinion");
        opinion.setText("Text of the opinion");
        opinion.setState(os.getValue());
        return opinion;
    }
    
	@RequestMapping(value = "/myopinion", method = RequestMethod.GET)
	@ResponseBody ResponseDto<Object> getMyTasks(Long userId) {

		List<Opinion> list;

		try {
			if (userId == null) {
				throw (new MessageException("No or invalid arguments provided"));
			}

			list = businessDelegate.getOpinionByUser(userId);

		} catch (MessageException e) {
			logger.error("CustomException while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}

		catch (Exception e) {
			logger.error("Exception while fetching the tasks " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}

		return new ResponseDto<Object>(true, list, "List Fetched Successfully");
	}
}
