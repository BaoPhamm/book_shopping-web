import axios from "axios";
import AuthHeader from "../AuthHeader";

const MANAGE_ADMIN_API_BASE_URL = "http://localhost:8080/api/v1/manage-admin";

class ManageAdminService {
  async getAllAdmins(pageNumber) {
    const response = await axios
      .get(MANAGE_ADMIN_API_BASE_URL, {
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

  async getAdminById(adminId) {
    const response = await axios
      .get(MANAGE_ADMIN_API_BASE_URL + "/" + adminId, {
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

  async deleteAdmin(adminId) {
    const response = await axios
      .delete(MANAGE_ADMIN_API_BASE_URL + "/" + adminId, {
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

  async blockAdmin(adminId) {
    const response = await axios
      .put(MANAGE_ADMIN_API_BASE_URL + "/block", adminId, {
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

  async unBlockAdmin(adminId) {
    const response = await axios
      .put(MANAGE_ADMIN_API_BASE_URL + "/unblock", adminId, {
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
        MANAGE_ADMIN_API_BASE_URL + "/role/add-to-user",
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
        MANAGE_ADMIN_API_BASE_URL + "/role/remove-from-user",
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

  async getTotalAdmins() {
    const response = await axios
      .get(MANAGE_ADMIN_API_BASE_URL + "/total", {
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

export default new ManageAdminService();
