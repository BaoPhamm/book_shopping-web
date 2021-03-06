import React, { useState } from "react";
import styled from "styled-components";
import { mobile } from "../responsive";
import { Link, Navigate } from "react-router-dom";
import AuthService from "../services/user/AuthService";
import { useSelector, useDispatch } from "react-redux";
import { loginAction, loginSelector } from "../store/reducers/loginSlice";
import Alert from "@mui/material/Alert";
import Collapse from "@mui/material/Collapse";
import Box from "@mui/material/Box";

const Container = styled.div`
  width: 98vw;
  height: 98vh;
  // background: linear-gradient(
  //     rgba(255, 255, 255, 0.5),
  //     rgba(255, 255, 255, 0.5)
  //   ),
  //   url("https://images.pexels.com/photos/6984661/pexels-photo-6984661.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
  //     center;
  background-color: #e6fff9;
  background-size: cover;
  display: flex;
  align-items: center;
  margin-top: 2rem;
  justify-content: center;
`;

const Wrapper = styled.div`
  width: 28%;
  padding: 20px;
  background-color: white;
  text-align: center;
  ${mobile({ width: "75%" })}
`;

const Title = styled.h1`
  font-size: 30px;
  font-weight: 300;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Input = styled.input`
  flex: 1;
  min-width: 90%;
  margin: 10px 0;
  padding: 10px;
`;

const Button = styled.button`
  width: 50%;
  border: none;
  padding: 15px 20px;
  background-color: teal;
  color: white;
  cursor: pointer;
  margin-bottom: 1rem;
  margin-top: 1rem;
`;

const TextLink = styled.a`
  margin: 0.5rem;
  font-size: 12px;
  text-decoration: underline;
  cursor: pointer;
`;
const TextLinkNoneUnderLine = styled.p`
  margin: 0.5rem;
  font-size: 12px;
  text-decoration: none;
  cursor: pointer;
`;

const Login = () => {
  const [formUsername, setUsername] = useState("");
  const [formPassword, setPassword] = useState("");
  const [openAlert, setOpenAlert] = useState(false);
  const [messageAlert, setmessageAlert] = useState("");

  const loginInfo = useSelector(loginSelector);
  const dispatch = useDispatch();

  const changeFormUsername = (event) => {
    setUsername(event.target.value);
  };
  const changeFormPassword = (event) => {
    setPassword(event.target.value);
  };

  const handleLoginSubmit = (event) => {
    event.preventDefault();

    const authenticationRequest = JSON.stringify({
      username: formUsername,
      password: formPassword,
    });

    AuthService.login(authenticationRequest).then(async (res) => {
      if (res.status === 400) {
        await setmessageAlert("Username and password could not be blank!");
        setOpenAlert(true);
      } else if (res.status === 403) {
        await setmessageAlert("Wrong username and password.");
        setOpenAlert(true);
      } else if (res.status === 200) {
        let loginResponse = {
          token: res.data.token,
          refreshToken: res.data.refreshToken,
          userRoles: res.data.userRoles,
          username: res.data.username,
          isBlocked: res.data.isBlocked,
        };
        dispatch(loginAction(loginResponse));
        alert("Login successfully");
      }
    });
  };

  return !loginInfo.isLoading ? (
    !loginInfo.isLogged ? (
      <Container>
        <Wrapper>
          <Title>SIGN IN</Title>
          <Form onSubmit={handleLoginSubmit}>
            <Input
              type="text"
              value={formUsername}
              placeholder="username"
              onChange={changeFormUsername}
            />
            <Input
              type="password"
              placeholder="password"
              value={formPassword}
              onChange={changeFormPassword}
            />
            <Button>LOGIN</Button>
            <TextLinkNoneUnderLine>
              DON'T HAVE AN ACCOUNT?
            </TextLinkNoneUnderLine>
            <TextLink>
              <Link to="/register" style={{ color: "black" }}>
                CREATE A NEW ACCOUNT
              </Link>
            </TextLink>
            <Box sx={{ width: "100%" }}>
              <Collapse in={openAlert}>
                <Alert variant="outlined" severity="error">
                  {messageAlert}
                </Alert>
              </Collapse>
            </Box>
          </Form>
        </Wrapper>
      </Container>
    ) : (
      <Navigate to="/" />
    )
  ) : (
    ""
  );
};

export default Login;
