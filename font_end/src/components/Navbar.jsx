import React, { useEffect } from "react";
import { Badge } from "@material-ui/core";
import { Search, ShoppingCartOutlined } from "@material-ui/icons";
import styled from "styled-components";
import { mobile } from "../responsive";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import { loginSelector } from "../store/reducers/loginSlice";
import { loginAction } from "../store/reducers/loginSlice";
import { logoutAction } from "../store/reducers/loginSlice";
import { useDispatch } from "react-redux";
import UserService from "../services/user/UserService";

const Container = styled.div`
  height: 60px;
  ${mobile({ height: "50px" })}
`;

const Wrapper = styled.div`
  padding: 10px 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  ${mobile({ padding: "10px 0px" })}
`;

const Left = styled.div`
  flex: 1;
  display: flex;
  align-items: center;
`;

const Language = styled.span`
  font-size: 14px;
  cursor: pointer;
  ${mobile({ display: "none" })}
`;

const SearchContainer = styled.div`
  border: 0.5px solid lightgray;
  display: flex;
  align-items: center;
  margin-left: 25px;
  padding: 5px;
`;

const Input = styled.input`
  border: none;
  ${mobile({ width: "50px" })}
`;

const Center = styled.div`
  flex: 1;
  text-align: center;
`;

const Logo = styled.h1`
  font-weight: bold;
  ${mobile({ fontSize: "24px" })}
`;
const Right = styled.div`
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  ${mobile({ flex: 2, justifyContent: "center" })}
`;

const MenuItem = styled.div`
  font-size: 14px;
  cursor: pointer;
  margin-left: 25px;
  textdecoration: "none";
  ${mobile({ fontSize: "12px", marginLeft: "10px" })}
`;
const LogoutButton = styled.button`
  width: 30%;
  border: none;
  padding: 0.5rem;
  background-color: #e6fff9;
  color: black;
  cursor: pointer;
  margin-left: 1rem;
`;

const Navbar = () => {
  const dispatch = useDispatch();

  const loginInfo = useSelector(loginSelector);

  useEffect(() => {
    const userLoginInfo = JSON.parse(localStorage.getItem("userLoginInfo"));
    if (userLoginInfo && userLoginInfo.token) {
      UserService.getUserInfo().then(async (res) => {
        console.log(res.status);
        if (res.status === 403) {
          UserService.logout();
          dispatch(logoutAction());
        }
      });
      let loginInfo = {
        token: userLoginInfo.token,
        refreshToken: userLoginInfo.refreshToken,
        userRoles: userLoginInfo.userRoles,
        username: userLoginInfo.username,
      };

      dispatch(loginAction(loginInfo));
    }
  }, []);

  const OnclickLogoutHandle = () => {
    UserService.logout();
    dispatch(logoutAction());
  };

  return (
    <Container>
      <Wrapper>
        <Left>
          <Language>EN</Language>
          <SearchContainer>
            <Input placeholder="Search" />
            <Search style={{ color: "gray", fontSize: 16 }} />
          </SearchContainer>
        </Left>
        <Center>
          <Logo>
            <Link to="/" style={{ textDecoration: "none", color: "black" }}>
              BOOKSTORE.
            </Link>
          </Logo>
        </Center>
        <Right>
          <MenuItem>
            {loginInfo.isLogged ? (
              <Link
                to="/profile"
                style={{ textDecoration: "none", color: "black" }}
              >
                {"Hi " + loginInfo.username}
              </Link>
            ) : (
              <Link
                to="/login"
                style={{ textDecoration: "none", color: "black" }}
              >
                LOG IN
              </Link>
            )}
          </MenuItem>
          {loginInfo.isLogged ? (
            <LogoutButton onClick={OnclickLogoutHandle}>LOG OUT</LogoutButton>
          ) : (
            ""
          )}
          <MenuItem>
            <Link to="/cart" style={{ textDecoration: "none", color: "black" }}>
              <Badge badgeContent={0} color="primary">
                <ShoppingCartOutlined />
              </Badge>
            </Link>
          </MenuItem>
        </Right>
      </Wrapper>
    </Container>
  );
};

export default Navbar;
