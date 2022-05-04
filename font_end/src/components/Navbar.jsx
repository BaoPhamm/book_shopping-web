import React, { useEffect } from "react";
import { Badge } from "@material-ui/core";
import LogoutIcon from "@mui/icons-material/Logout";
import { Search, ShoppingCartOutlined } from "@material-ui/icons";
import styled from "styled-components";
import { mobile } from "../responsive";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { loginSelector } from "../store/reducers/loginSlice";
import { loginAction, logoutAction } from "../store/reducers/loginSlice";
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

const LogoutText = styled.p`
  font-size: 14px;
  margin-right: 0.4rem;
  cursor: pointer;
`;

const SearchContainer = styled.div`
  border: 0.5px solid lightgray;
  display: flex;
  align-items: center;
  margin-left: 25px;
  padding: 5px;
`;

const LogoutContainer = styled.div`
  display: flex;
  align-items: center;
  margin-left: 0.8rem;
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
  margin-left: 1rem;
  textdecoration: "none";
  ${mobile({ fontSize: "12px", marginLeft: "0.8rem" })}
`;

const Navbar = () => {
  const dispatch = useDispatch();
  const loginInfo = useSelector(loginSelector);

  useEffect(() => {
    function fetchData() {
      const userLoginInfo = JSON.parse(localStorage.getItem("userLoginInfo"));
      if (userLoginInfo && userLoginInfo.token) {
        UserService.getUserInfo().then(async (res) => {
          console.log(res.status);
          if (res.status === 403) {
            dispatch(logoutAction());
          } else if (res.status === 200) {
            let loginInfo = {
              token: userLoginInfo.token,
              refreshToken: userLoginInfo.refreshToken,
              userRoles: userLoginInfo.userRoles,
              username: userLoginInfo.username,
            };
            dispatch(loginAction(loginInfo));
          }
        });
      } else {
        dispatch(logoutAction());
      }
    }
    fetchData();
  }, []);

  const OnclickLogoutHandle = () => {
    dispatch(logoutAction());
  };

  const RightContent = () => {
    return (
      <Right>
        <MenuItem>
          {loginInfo.isLogged ? (
            <Link
              to="/profile"
              style={{
                fontSize: "16px",
                textDecoration: "none",
                color: "black",
              }}
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
          <LogoutContainer>
            <LogoutText onClick={OnclickLogoutHandle}>Log out</LogoutText>
            <LogoutIcon
              cursor="pointer"
              onClick={OnclickLogoutHandle}
              style={{ color: "black", fontSize: 16 }}
            />
          </LogoutContainer>
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
    );
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
        <RightContent />
      </Wrapper>
    </Container>
  );
};

export default Navbar;
