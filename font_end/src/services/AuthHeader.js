export default function AuthHeader() {
  const userLoginInfo = JSON.parse(localStorage.getItem("userLoginInfo"));
  if (userLoginInfo && userLoginInfo.token) {
    return {
      Authorization: "Bearer " + userLoginInfo.token,
      "Content-Type": "application/json",
    };
  } else {
    return { "Content-Type": "application/json" };
  }
}
