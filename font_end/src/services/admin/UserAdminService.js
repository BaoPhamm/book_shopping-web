import axios from "axios";
import AuthHeader from "../AuthHeader";

const ADMIN_USER_API_BASE_URL = "http://localhost:8080/api/v1/admin/users";

class UserAdminService {
  async getAllUsers(pageNumber) {
    const response = await axios
      .get(ADMIN_USER_API_BASE_URL, {
        params: { page: pageNumber },
        headers: AuthHeader(),
      })
      .then(function (response) {
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }

  async getUserById(userId) {
    const response = await axios
      .get(ADMIN_USER_API_BASE_URL + "/" + userId, {
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

  async deleteUser(userId) {
    const response = await axios
      .delete(ADMIN_USER_API_BASE_URL + "/" + userId, {
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

  async blockUser(userId) {
    const response = await axios
      .put(ADMIN_USER_API_BASE_URL + "/block", userId, {
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

  async unBlockUser(userId) {
    const response = await axios
      .put(ADMIN_USER_API_BASE_URL + "/unblock", userId, {
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

  async getTotalUsers() {
    const response = await axios
      .get(ADMIN_USER_API_BASE_URL + "/total", {
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

export default new UserAdminService();
