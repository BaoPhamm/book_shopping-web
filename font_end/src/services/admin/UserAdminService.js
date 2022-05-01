import axios from "axios";

const ADMIN_USER_API_BASE_URL = "http://localhost:8080/api/v1/admin/users";

class UserAdminService {
  async getAllUsers(token) {
    const response = await axios
      .get(ADMIN_USER_API_BASE_URL, {
        headers: {
          Authorization: "Bearer " + token,
        },
      })
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    return response;
  }

  async getUserById(token, userId) {
    const response = await axios
      .get(ADMIN_USER_API_BASE_URL + "/" + userId, {
        headers: {
          Authorization: "Bearer " + token,
        },
      })
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    return response;
  }

  async deleteUser(token, userId) {
    const response = await axios
      .delete(ADMIN_USER_API_BASE_URL + "/" + userId, {
        headers: {
          Authorization: "Bearer " + token,
        },
      })
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    return response;
  }

  async addRoleToUser(token, addRoleToUserRequest) {
    const response = await axios
      .post(
        ADMIN_USER_API_BASE_URL + "/role/add-to-user",
        addRoleToUserRequest,
        {
          headers: {
            Authorization: "Bearer " + token,
          },
        }
      )
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    return response;
  }

  async removeRoleFromUser(token, removeRoleFromUserRequest) {
    const response = await axios
      .post(
        ADMIN_USER_API_BASE_URL + "/role/remove-from-user",
        removeRoleFromUserRequest,
        {
          headers: {
            Authorization: "Bearer " + token,
          },
        }
      )
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    return response;
  }
}

export default new UserAdminService();
