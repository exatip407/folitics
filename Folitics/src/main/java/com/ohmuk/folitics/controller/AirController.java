package com.ohmuk.folitics.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IAirBusinessDeligate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;

@Controller
@RequestMapping("/componentair")
public class AirController {

	private static Logger logger = LoggerFactory.getLogger(AirController.class);

	@Autowired
	private IAirBusinessDeligate airDelegate;

	/**
	 * This method is used to add the record.
	 * 
	 * @param air
	 * @return ResponseDto<Share>
	 */
	@RequestMapping(value = "/air", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Object> add(
			@RequestBody AirShareDataBean airShareBean) {

		logger.info("Inside add Air");

		try {
			Object shared = airDelegate.create(airShareBean);

			if (shared != null) {
				return new ResponseDto<Object>(true, shared,
						"Successfully added air");
			}
		} catch (MessageException e) {
			logger.error("CustomException while adding the air " + e);
			return new ResponseDto(false, null, e.getMessage());

		} catch (Exception e) {
			logger.error("Exception while adding air " + e);
			return new ResponseDto(false, null, e.getMessage());
		}
		return new ResponseDto<Object>("Something went wrong, can't add air",
				false);
	}

	@RequestMapping(value = "/isAired", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Boolean> isAired(
			@RequestBody AirShareDataBean airShareBean) {

		logger.info("Inside add Air");

		try {
			Boolean shared = airDelegate.isComponentAiredByUser(airShareBean);

			if (shared) {
				return new ResponseDto<Boolean>(true, shared,
						"Component is Aired by user");
			}
		} catch (MessageException e) {
			logger.error("CustomException while adding the air " + e);
			return new ResponseDto(false, null, e.getMessage());

		} catch (Exception e) {
			logger.error("Exception while adding air " + e);
			return new ResponseDto(false, null, e.getMessage());
		}
		return new ResponseDto<Boolean>("Something went wrong, can't add air",
				false);
	}

	@RequestMapping(value = "/getUserList", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<List<User>> findUserListForComponent(
			@RequestBody AirShareDataBean airShareBean) {

		logger.info("Inside get UserList for Air");

		try {
			List<User> users = airDelegate
					.findUserListForComponent(airShareBean);

			if (users != null) {
				return new ResponseDto<List<User>>(true, users,
						"Successfully got user List");
			}
		} catch (MessageException e) {
			logger.error("CustomException while adding the air " + e);
			return new ResponseDto(false, null, e.getMessage());

		} catch (Exception e) {
			logger.error("Exception while adding air " + e);
			return new ResponseDto(false, null, e.getMessage());
		}
		return new ResponseDto<List<User>>(
				"Something went wrong, can't fethch users", false);
	}

	@RequestMapping(value = "/getAirCount", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Long> getShareCount(String componentType,
			Long componentId) {

		logger.info("Inside Count  for  Air");
		AirShareDataBean airShareBean = new AirShareDataBean();

		airShareBean.setComponentId(componentId);
		airShareBean.setComponentType(componentType);

		try {
			Long count = airDelegate.getAirCount(airShareBean);

			if (count != null) {
				return new ResponseDto<Long>(true, count,
						"Successfully got Count");
			}
		} catch (MessageException e) {
			logger.error("CustomException while adding the air " + e);
			return new ResponseDto(false, null, e.getMessage());

		} catch (Exception e) {
			logger.error("Exception while getting airCount " + e);
			return new ResponseDto(false, null, e.getMessage());
		}
		return new ResponseDto<Long>(
				"Something went wrong, can't fetch users Count", false);
	}
}
