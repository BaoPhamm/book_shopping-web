import axios from "axios";
import AuthHeader from "../AuthHeader";

const RATING_API_BASE_URL = "http://localhost:8080/api/v1/user/rating";

class RatingService {
  async postRating(ratingRequest) {
    const response = await axios
      .post(RATING_API_BASE_URL, ratingRequest, {
        headers: AuthHeader(),
      })
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }
  async getUserRatingPoingBook(bookId) {
    const response = await axios
      .get(RATING_API_BASE_URL + "/" + bookId, {
        headers: AuthHeader(),
      })
      .then(function (response) {
        return response;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }
}

export default new RatingService();
