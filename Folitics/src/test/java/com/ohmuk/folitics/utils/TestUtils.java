package com.ohmuk.folitics.utils;

import java.io.File;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestUtils {
    public static final String BASE_URL = "http://localhost:8080";
    public static final String TEST_IMAGE = "." + File.separator + "testdata" + File.separator + "user"
            + File.separator + "testimage.jpg";

    // Required to Generate JSON content from Java objects
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

    public interface EVENT_CONTROLLER_APIS {
        String ADD = "/event/add";
        String EDIT = "/event/edit";
        String DELETE = "/event/delete";
        String FIND = "/event/find";
    }

    public interface CATEGORY_CONTROLLER_APIS {
        String ADD = "/category/add";
        String EDIT = "/category/edit";
        String DELETE = "/category/delete";
        String DELETE_BY_ID = "/category/deleteById";
        String FIND = "/category/find";
        String GETALL = "/category/getAll";
        String SEARCH = "/category/search";
        String PERMANENT_DELETE = "/category/deleteFromDb";
        String GET_ACTIVE_CATEOGRY = "/category/getActiveCategories";
    }

    public interface SENTIMENT_CONTROLLER_APIS {
        String ADD = "/sentiment/add";
        String EDIT = "/sentiment/edit";
        String DELETE = "/sentiment/delete";
        String DELETE_BY_ID = "/sentiment/deletebyid";
        String FIND = "/sentiment/find";
        String PERMANENT_DELETE = "/sentiment/deleteFromDb";
        String ADD_SENTIMENT = "/sentiment/add";
        String RELATED_SENTIMENT_SUGGESTION = "/sentiment/getAllSentimentNotIn";
        String GET_SENTIMENT_INDICATOR = "/sentiment/getAllSentimentIndicator";
    }

    public interface OPINION_CONTROLLER_APIS {
        String ADD = "/opinion/addOpinion";
        String EDIT = "/opinion/edit";
        String DELETE = "/opinion/delete";
        String DELETE_BY_ID = "/opinion/deleteById";
        String FIND = "/opinion/find";
        String PERMANENT_DELETE = "/opinion/deleteFromDb";
        String PERMANENT_DELETE_BY_ID = "/opinion/deleteFromDbById";
    }

    public interface LIKE_CONTROLLER_APIS {
        String LIKE = "/like/like";
        String FIND = "/like/find";
        String DELETE = "/like/delete";
        String DISLIKE = "/like/dislike";
        String UNLIKE = "/like/unlike";
        String UNDISLIKE = "/like/undislike";
        String GET_LIKES = "/like/getLikeAndDislikeCount";
    }

    public interface RESPONSE_CONTROLLER_APIS {
        String ADD = "/response/add";
        String EDIT = "/response/edit";
        String DELETE = "/response/delete";
        String DELETE_BY_ID = "/response/deletebyid";
        String FIND = "/response/find";
        String PERMANENTDELETE = "/response/deleteFromDBByid";
        String GET_BY_OPINION_ID = "/response/getByOpinionId";
        String GET_BY_USER_ID = "/response/getByUserId";
        String GET_BY_TASK_ID = "/response/getByOpinionId";
    }

    public interface USER_CONTROLLER_APIS {
        String ADD = "/user/add";
        String ADD_MULTIPART = "/user/addUserMultipart";
        String EDIT = "/user/edit";
        String DELETE = "/user/delete";
        String DELETE_BY_ID = "/user/deleteById";
        String FIND = "/user/find";
        String RESET_PASSWORD = "/user/resetPassword";
        String PERMANENT_DELETE_BY_ID = "/user/deleteFromDbById";
        String DELETE_CONNECTION = "/user/deleteConnection";
        String GET_ALL = "/user/getAll";
        String PERMANENT_DELETE = "/user/deleteFromDb";
        String UPLOADIMAGE = "/user/uploadimage";
        String SAVE_USER_EMAIL_NOTIFICATION = "/user/saveUserEmailNotificationSettings";
        String SAVE_USER_PRIVACY_SETTINGS = "/user/saveUserPrivacySettings";
        String UPDATE_USER_EMAIL_NOTIFICATION = "/user/updateUserEmailNotificationSettings";
        String UPDATE_USER_PRIVACY_SETTINGS = "/user/updateUserPrivacySettings";
        String GETALL_USER_EMAIL_NOTIFICATION = "/user/getAllUserEmailNotificationSettings";
        String SAVE_USER_UI_NOTIFICATION = "/user/saveUserUINotification";
        String UPDATE_USER_UI_NOTIFICATION = "/user/updateUserUINotification";
        String GETALL_USER_UI_NOTIFICATION = "/user/getAllUserUINotification";
        String BLOCK_USER = "/user/blockUser";
        String UN_BLOCK_USER = "/user/unBlockUser";
        String ADD_CONNECTION = "/user/addConnection";
        String UPDATE_CONNECTION_STATUS = "/user/updateConnectionStatus";
        String GET_USER_CONNECTION = "/user/getUserConnection";

    }

    public interface COMPONENT_CONTROLLER_APIS {
        String ADD = "/component/add";
        String EDIT = "/component/edit";
        String DELETE = "/component/delete";
        String DELETE_BY_ID = "/component/deleteById";
        String FIND = "/component/find";
        String GETFROMITEM = "/component/getfromitem";
    }

    public interface POLL_CONTROLLER_APIS {
        String ADD = "/poll/add";
        String EDIT = "/poll/edit";
        String DELETE = "/poll/delete";
        String DELETE_BY_ID = "/poll/deleteById";
        String FIND = "/poll/find";
        String PERMANENT_DELETE = "/poll/deleteFromDb";
        String GET_ACTIVE_POLLS = "/poll/getActivePolls";
        String GET_POLLS_FOR_SENTIMENT = "/poll/getPollForSentiment";
        String PERMANENT_DELETE_BY_ID = "/poll/deleteFromDbById";
    }

    public interface RSS_FEED_CONTROLLER_APIS {
        String ADD_SOURCE = "/rssfeed/addsource";
        String LOAD_FEED = "/rssfeed/loadfeed";
        String DISABLE_SOURCE = "/rssfeed/disablesource";
        String GET_ALL_SOURCES = "/rssfeed/getallsources";
        String FIND_SOURCE_BY_NAME = "/rssfeed/findsource";
        String SEARCH_FEED = "/rssfeed/search";
        String CLEAR_FEED = "/rssfeed/clearfeed";
    }

    public interface MILESTONE_CONTROLLER_APIS {
        String ADD_BY_GPI = "/milestone/add";
        String DELETE_BY_GPI = "/milestone/delete";
        String FIND_ALL = "/milestone/findAll";
    }

    public interface GPI_POINTS_CONTROLLER_APIS {
        String ADD = "/gpiPoint/add";
        String SOFT_DELETE_BY_ID = "/gpiPoint/deletebyid";
        String HARD_DELETE_BY_ID = "/gpiPoint/hardDeleteById";
    }

    public interface IMAGE_CONTROLLER_APIS {
        String GET_IMAGE = "/image/getimage";
        String GET_IMAGES = "/image/getimages";
    }

    public interface INDICATOR_DATA_CONTROLLER_APIS {
        String ADD = "/indicatordata/add";
        String EDIT = "/indicatordata/edit";
        String GET_ALL = "/indicatordata/getAll";
        String DELETE_BY_ID = "/indicatordata/deletebyid";
        String DELETE = "/indicatordata/delete";
        String PERMANENT_DELETE = "/indicatordata/deleteFromDB";
        String FIND = "/indicatordata/find";
        String GET_HEADER_DATA = "/indicatordata/getHeaderData";
        String GET_PROMISE_DATA = "/indicatordata/getPromiseData";
    }

    public interface INDICATOR_THRESHOLD_DATA_CONTROLLER_APIS {
        String ADD = "/indicatorthreshold/add";
        String EDIT = "/indicatorthreshold/edit";
        String GET_ALL = "/indicatorthreshold/getall";
        String DELETE_BY_ID = "/indicatorthreshold/deletebyid";
        String DELETE = "/indicatorthreshold/delete";
        String PERMANENT_DELETE = "/indicatorthreshold/deleteFromDB";
        String FIND = "/indicatorthreshold/find";
        String GET_HEADER_DATA = "/indicatorthreshold/getHeaderData";
        String GET_PROMISE_DATA = "/indicatorthreshold/getPromiseData";
    }

    public interface GOVT_SCHEME_DATA_CONTROLLER_APIS {
        String ADD = "/govtschemedata/add";
        String EDIT = "/govtschemedata/edit";
        String GET_ALL = "/govtschemedata/getall";
        String DELETE_BY_ID = "/govtschemedata/deletebyid";
        String DELETE = "/govtschemedata/delete";
        String PERMANENT_DELETE = "/govtschemedata/deleteFromDB";
        String FIND = "/govtschemedata/find";
        String PERMANENT_DELETE_BY_ID = "/govtschemedata/deleteFromDbById";
        String GET_HEADER_DATA = "/govtschemedata/getHeaderData";
        String GET_PROMISE_DATA = "/govtschemedata/getPromiseData";
    }

    public interface INDICATOR_WEIGHTED_DATA_CONTROLLER_APIS {
        String ADD = "/indicatorweighteddata/add";
        String EDIT = "/indicatorweighteddata/edit";
        String GET_ALL = "/indicatorweighteddata/getAll";
        String DELETE_BY_ID = "/indicatorweighteddata/deletebyid";
        String PERMANENT_DELETE = "/indicatorweighteddata/deleteFromDB";
        String DELETE = "/indicatorweighteddata/delete";
        String FIND = "/indicatorweighteddata/find";
    }

    public interface CHART_CONTROLLER_APIS {
        String GET = "/chart/getChart";
    }

    public interface SHARE_CONTROLLER_APIS {
        String SHARE = "/componentShare/share";
        String GET_SHARE_COUNT = "/componentShare/getShareCount";
        String FIND = "/componentShare/find";
        String DELETE = "/componentShare/delete";
    }

    public interface VERDICT_CONTROLLER_APIS {
        String ADD = "/verdict/add";
        String FIND = "/verdict/find";
        String FIND_ALL = "/verdict/getAll";
        String GET_VERDICT_FOR_SENTIMENT = "/verdict/getVerdictForSentiment";
        String EDIT = "/verdict/edit";
        String DELETE = "/verdict/delete";
        String DELETE_BY_ID = "/verdict/deleteById";
    }

    public interface USER_MONETIZATION_CONTROLLER_APIS {
        String ADD_ACTION = "/userMonetization/addAction";
        String ADD_CONTEST = "/userMonetization/addContest";
        String GET_BY_ID = "/userMonetization/getById";
        String GET_BY_USER_ID = "/userMonetization/getByUserId";
        String GET_BY_DATE = "/userMonetization/getByDate";

    }

    public interface MONETIZATION_CONFIG_CONTROLLER_APIS {
        String ADD = "/monetizationConfig/add";
        String EDIT = "/monetizationConfig/edit";
        String READ = "/monetizationConfig/read";
        String GET_ALL = "/monetizationConfig/getAll";
        String DELETE_BY_ID = "/monetizationConfig/deleteById";
        String DELETE = "/monetizationConfig/delete";

    }

    public interface COMPONENTLIKE_CONTROLLER_APIS {
        String FIND = "/event/find";
    }

    public static HttpHeaders getHttpHeaders(MediaType type) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(type);
        return requestHeaders;
    }

    public static HttpEntity<HttpHeaders> getHttpEntityHttpHeaders() {
        return new HttpEntity<>(TestUtils.getHttpHeadersApplicationJson());
    }

    // MediaType.APPLICATION_JSON
    public static HttpHeaders getHttpHeadersApplicationJson() {
        return getHttpHeaders(MediaType.APPLICATION_JSON);
    }

    public static HttpEntity<String> getHttpEntity(Map<String, Object> requestBody) throws JsonProcessingException {
        // Creating http entity object with request body and headers
        return new HttpEntity<String>(GSON.toJson(requestBody), TestUtils.getHttpHeadersApplicationJson());
    }

    public interface EVENT_REPORT_SCORE_DATA_CONTROLLER_APIS {
        String ADD = "/event/add";
        String EDIT = "/event/edit";
        String GET_ALL = "/event/getall";
        String DELETE_BY_ID = "/event/deletebyid";
        String PERMANENT_DELETE = "/event/deleteFromDB";
        String DELETE = "/event/delete";
        String FIND = "/event/find";
    }

    public interface TASK_CONTROLLER_APIS {
        String ADD = "/task/addTask";
        String DELETE = "/task/removeTask";
        String EDIT = "/task/editTask";
        String FIND = "/task/search";
        String FIND_All = "/task/getAll";
        String GET_CATEGORIES = "/task/getTaskCategories";
        String GET_DEPARTMENTS = "/task/getAllDepartments";
    }

    public interface CONTEST_CONTROLLER_APIS {
        String ADD = "/contest/addContest";
        String ADD_PARTICIPANT = "/contest/addParticipants";
        String GENERATE_WINNER = "/contest/genrateContestWinner";
        String DELETE = "/contest/deleteFromDb";
        String SOFT_DELETE = "/contest/delete";
        String EDIT = "contest/edit";
        String GET_BY_DATE = "/contest/getByDate";
        String FIND_All = "/contest/getAll";
        String GET_ALL_WINNER = "/contest/readAllWinners";
        String GET_ACTIVE_CONTEST = "/contest/readAllActiveContest";
        String GET_ALL_PARTICIPANTS = "/contest/readAllParticipants";
    }

    
	public interface LUCKYDRAW_CONTROLLER_APIS {

		String DELETE = "/luckyDraw/delete";
		String HARD_DELETE = "/luckyDraw/deleteFromDb";
		String EDIT = "luckyDraw/edit";
		String FIND = "/luckyDraw/find";
		String FIND_All = "/luckyDraw/getAll";
		String GET_ACTIVE_LUCKYDRAW = "/luckyDraw/readAllActiveLuckyDraw";

	}

public interface FACT_CONTROLLER_APIS {
        String ADD = "/fact/submitFact";
        String DELETE = "/fact/delete";
        String DELETE_FROM_DB = "/fact/deleteFromDb";
        String EDIT = "/fact/editFact";
        String GET_FACT = "/fact/getFact";
        String GET_ALL = "/fact/getAllFacts";
        String GET_ALL_BY_STATUS = "/fact/getAllFactsByStatus";
        String GET_ALL_BY_USERID = "/fact/getAllFactsByUserId";
        String GET_ALL_BY_PARENTID_AND_PARENTTYPE = "/fact/getAllFactsByParentIdAndParentType";
        String APPROVE_FACT = "/fact/approveFact";
        String REJECT_FACT = "/fact/rejectFact";

    }

}