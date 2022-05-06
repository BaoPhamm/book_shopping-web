import React, { useState, useEffect } from "react";
import { Add, Remove } from "@material-ui/icons";
import styled from "styled-components";
import { mobile } from "../responsive";
import { useParams } from "react-router-dom";
import BookService from "../services/user/BookService";
import { Navigate } from "react-router-dom";

const Container = styled.div``;

const Wrapper = styled.div`
  padding: 50px;
  display: flex;
  ${mobile({ padding: "10px", flexDirection: "column" })}
`;

const ImgContainer = styled.div`
  flex: 1;
`;

const Image = styled.img`
  width: 100%;
  height: 90vh;
  object-fit: cover;
  ${mobile({ height: "40vh" })}
`;

const InfoContainer = styled.div`
  flex: 1;
  padding: 0px 50px;
  ${mobile({ padding: "10px" })}
`;

const Title = styled.h1`
  font-weight: 250;
  font-size: 60px;
`;

const Desc = styled.p`
  margin: 20px 0px;
  font-size: 20px;
`;

const Price = styled.span`
  font-weight: 100;
  font-size: 40px;
`;

const AddContainer = styled.div`
  width: 50%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 1rem;
  ${mobile({ width: "100%" })}
`;

const AmountContainer = styled.div`
  display: flex;
  align-items: center;
  font-weight: 700;
`;

const Amount = styled.span`
  width: 30px;
  height: 30px;
  border-radius: 10px;
  border: 1px solid teal;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0px 5px;
`;

const Button = styled.button`
  padding: 15px;
  border: 2px solid teal;
  background-color: white;
  cursor: pointer;
  font-weight: 500;

  &:hover {
    background-color: #f8f4f4;
  }
`;

const Product = () => {
  const [book, setBook] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isBookExist, setIsBookExist] = useState(false);

  let { id } = useParams();
  console.log(id);

  useEffect(() => {
    GetBook();
  }, []);

  const GetBook = async () => {
    await setIsLoading(true);
    BookService.getBookById(id).then(async (res) => {
      console.log(res);
      if (res.status === 404) {
        await setIsBookExist(false);
      } else if (res.status === 200) {
        await setIsBookExist(true);
        await setBook(res.data);
      }
      await setIsLoading(false);
    });
  };

  return !isLoading ? (
    isBookExist ? (
      <Container>
        <Wrapper>
          <ImgContainer>
            <Image src={book.imgSrc} />
          </ImgContainer>
          <InfoContainer>
            <Title>{book.title}</Title>
            <Desc>{book.description}</Desc>
            <Price>{book.price + " VND"}</Price>
            <AddContainer>
              <AmountContainer>
                <Remove />
                <Amount>1</Amount>
                <Add />
              </AmountContainer>
              <Button>ADD TO CART</Button>
            </AddContainer>
          </InfoContainer>
        </Wrapper>
      </Container>
    ) : (
      <Navigate to="/products/not-found" />
    )
  ) : (
    ""
  );
};

export default Product;
