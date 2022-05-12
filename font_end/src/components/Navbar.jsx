import React, { useState, useEffect } from "react";
import { Badge } from "@material-ui/core";
import LogoutIcon from "@mui/icons-material/Logout";
import { Search, ShoppingCartOutlined } from "@material-ui/icons";
import styled from "styled-components";
import { mobile } from "../responsive";
import { Link, useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import {
  loginSelector,
  loginAction,
  logoutAction,
  startLoadingAction,
  endLoadingAction,
} from "../store/reducers/loginSlice";
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
  font-size: 18px;
  margin-right: 0.2rem;
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
  margin-left: 1rem;
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
  const [isLoading, setIsLoading] = useState(true);
  let navigate = useNavigate();

  useEffect(() => {
    fetchData();
  }, [loginInfo.isLogged]);

  const fetchData = async () => {
    dispatch(startLoadingAction());
    await setIsLoading(true);
    const userLoginInfo = JSON.parse(localStorage.getItem("userLoginInfo"));
    if (userLoginInfo && userLoginInfo.token) {
      await UserService.getUserInfo().then(async (res) => {
        console.log(res.status);
        if (res.status === 403) {
          dispatch(logoutAction());
          alert("Please login again!");
          navigate("/login");
        } else if (res.status === 200) {
          if (res.data.blocked) {
            dispatch(logoutAction());
            alert("Your account is blocked!");
          } else {
            let loginInfo = {
              token: userLoginInfo.token,
              refreshToken: userLoginInfo.refreshToken,
              userRoles: userLoginInfo.userRoles,
              username: userLoginInfo.username,
              id: res.data.id,
              firstName: res.data.firstName,
              lastName: res.data.lastName,
              phoneNumber: res.data.phoneNumber,
              address: res.data.address,
              blocked: res.data.blocked,
            };
            dispatch(loginAction(loginInfo));
          }
        }
      });
    } else {
      dispatch(logoutAction());
    }
    await setIsLoading(false);
    dispatch(endLoadingAction());
  };

  const OnclickLogoutHandle = () => {
    dispatch(startLoadingAction());
    dispatch(logoutAction());
    dispatch(endLoadingAction());
  };

  const CenterContent = () => {
    return (
      <Center>
        {loginInfo.isAdmin ? (
          <Logo>
            <Link
              to="/admin"
              style={{ textDecoration: "none", color: "black" }}
            >
              BOOKSTORE MANAGEMENT
            </Link>
          </Logo>
        ) : (
          <Logo>
            <Link to="/" style={{ textDecoration: "none", color: "black" }}>
              BOOKSTORE.
            </Link>
          </Logo>
        )}
      </Center>
    );
  };
  const RightContent = () => {
    return (
      <Right>
        <MenuItem>
          {loginInfo.isLogged ? (
            <Link
              to="/profile"
              style={{
                fontSize: "20px",
                textDecoration: "none",
                color: "black",
                fontWeight: "10",
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
              style={{ color: "black", fontSize: 18 }}
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

  return !isLoading ? (
    <Container>
      <Wrapper>
        <Left>
          <Language>EN</Language>
          <SearchContainer>
            <Input placeholder="Search" />
            <Search style={{ color: "gray", fontSize: 16 }} />
          </SearchContainer>
        </Left>
        <CenterContent />
        <RightContent />
      </Wrapper>
    </Container>
  ) : (
    ""
  );
};

export default Navbar;
