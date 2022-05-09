import React from "react";
import {
  Facebook,
  Instagram,
  MailOutline,
  Phone,
  Room,
  Twitter,
} from "@material-ui/icons";
import styled from "styled-components";
import { mobile } from "../responsive";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import { loginSelector } from "../store/reducers/loginSlice";

const Container = styled.div`
  display: flex;
  ${mobile({ flexDirection: "column" })}
`;

const Left = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 20px;
`;

const Logo = styled.h1``;

const Desc = styled.p`
  margin: 20px 0px;
`;

const SocialContainer = styled.div`
  display: flex;
`;

const SocialIcon = styled.div`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  color: white;
  background-color: #${(props) => props.color};
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
`;

const Center = styled.div`
  flex: 1;
  padding: 20px;
  ${mobile({ display: "none" })}
`;

const Title = styled.h3`
  margin-bottom: 30px;
`;

const List = styled.ul`
  margin: 0;
  padding: 0;
  list-style: none;
  display: flex;
  flex-wrap: wrap;
`;

const ListItem = styled.li`
  width: 50%;
  margin-bottom: 10px;
`;

const Right = styled.div`
  flex: 1;
  padding: 20px;
  ${mobile({ backgroundColor: "#fff8f8" })}
`;

const ContactItem = styled.div`
  margin-bottom: 20px;
  display: flex;
  align-items: center;
`;

const Payment = styled.img`
  width: 50%;
`;

const Footer = () => {
  const loginInfo = useSelector(loginSelector);
  return !loginInfo.isLoading ? (
    !loginInfo.isAdmin ? (
      <Container>
        <Left>
          <Logo>BOOKSTORE.</Logo>
          <Desc>We only sale books.</Desc>
          <SocialContainer>
            <SocialIcon color="3B5999">
              <Facebook />
            </SocialIcon>
            <SocialIcon color="E4405F">
              <Instagram />
            </SocialIcon>
            <SocialIcon color="55ACEE">
              <Twitter />
            </SocialIcon>
          </SocialContainer>
        </Left>
        <Center>
          <Title>Useful Links</Title>
          <List>
            <ListItem>
              <Link to="/" style={{ textDecoration: "none", color: "black" }}>
                Home
              </Link>
            </ListItem>
            <ListItem>
              <Link
                to="/profile"
                style={{ textDecoration: "none", color: "black" }}
              >
                My Account
              </Link>
            </ListItem>
            <ListItem>Terms</ListItem>
            <ListItem>
              <Link
                to="/products"
                style={{ textDecoration: "none", color: "black" }}
              >
                Products
              </Link>
            </ListItem>
          </List>
        </Center>
        <Right>
          <Title>Contact</Title>
          <ContactItem>
            <Room style={{ marginRight: "10px" }} /> Etown4, HCMc, VN
          </ContactItem>
          <ContactItem>
            <Phone style={{ marginRight: "10px" }} /> +84 090 2345678
          </ContactItem>
          <ContactItem>
            <MailOutline style={{ marginRight: "10px" }} /> baobao@elite.java
          </ContactItem>
          <Payment src="https://i.ibb.co/Qfvn4z6/payment.png" />
        </Right>
      </Container>
    ) : (
      ""
    )
  ) : (
    ""
  );
};

export default Footer;
