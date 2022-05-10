import axios from "axios";
import AuthHeader from "../AuthHeader";

const ADMIN_ROLE_API_BASE_URL = "http://localhost:8080/api/v1/admin/role";

class RoleAdminService {
  async getRoleByName(roleName) {
    const response = await axios
      .get(ADMIN_ROLE_API_BASE_URL + "/" + roleName, {
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

  async getAllRoles() {
    const response = await axios
      .get(ADMIN_ROLE_API_BASE_URL, {
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

  async createRole(roleName) {
    const response = await axios
      .post(ADMIN_ROLE_API_BASE_URL, roleName, {
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

  async updateRole(roleName) {
    const response = await axios
      .put(ADMIN_ROLE_API_BASE_URL, roleName, {
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

  async deleteRole(roleId) {
    const response = await axios
      .delete(ADMIN_ROLE_API_BASE_URL + "/" + roleId, {
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
}

export default new RoleAdminService();
