import axios from "axios";
import AuthHeader from "../AuthHeader";

const USER_API_BASE_URL = "http://localhost:8080/api/v1/user";

class UserService {
  async getUserInfo() {
    const response = await axios
      .get(USER_API_BASE_URL + "/info", {
        headers: AuthHeader(),
      })
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error.response);
        return error.response;
      });
    return response;
  }

  async updateUserInfo(userRequest) {
    const response = await axios
      .put(USER_API_BASE_URL + "/info", userRequest, {
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

  async updateUserPassword(passwordResetRequest) {
    const response = await axios
      .put(USER_API_BASE_URL + "/password", passwordResetRequest, {
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

export default new UserService();
