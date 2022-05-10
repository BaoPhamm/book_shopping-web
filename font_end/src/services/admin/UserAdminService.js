import axios from "axios";
import AuthHeader from "../AuthHeader";

const ADMIN_USER_API_BASE_URL = "http://localhost:8080/api/v1/admin/users";

class UserAdminService {
  async getAllUsers() {
    const response = await axios
      .get(ADMIN_USER_API_BASE_URL, {
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

  async addRoleToUser(addRoleToUserRequest) {
    const response = await axios
      .post(
        ADMIN_USER_API_BASE_URL + "/role/add-to-user",
        addRoleToUserRequest,
        {
          headers: AuthHeader(),
        }
      )
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

  async removeRoleFromUser(removeRoleFromUserRequest) {
    const response = await axios
      .post(
        ADMIN_USER_API_BASE_URL + "/role/remove-from-user",
        removeRoleFromUserRequest,
        {
          headers: AuthHeader(),
        }
      )
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
}

export default new UserAdminService();
