import axios from "axios";

const ADMIN_ROLE_API_BASE_URL = "http://localhost:8080/api/v1/admin/role";

class RoleAdminService {
  async createRole(token, roleName) {
    const response = await axios
      .post(ADMIN_ROLE_API_BASE_URL, roleName, {
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
}

export default new RoleAdminService();
