import axios from "axios";

const ADMIN_ORDER_API_BASE_URL = "http://localhost:8080/api/v1/admin/order";

class OrderAdminService {
  async getAllOrders(token) {
    const response = await axios
      .get(ADMIN_ORDER_API_BASE_UR + "/orders", {
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

  async getUserOrdersByUsername(token, userName) {
    const response = await axios
      .get(ADMIN_ORDER_API_BASE_UR + "/" + userName, {
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

  async deleteOrder(token, orderId) {
    const response = await axios
      .delete(ADMIN_ORDER_API_BASE_UR + "/" + orderId, {
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

export default new OrderAdminService();
