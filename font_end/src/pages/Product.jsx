import React, { useState, useEffect } from "react";
import { Add, Remove } from "@material-ui/icons";
import styled from "styled-components";
import { mobile } from "../responsive";
import { useParams } from "react-router-dom";
import BookService from "../services/user/BookService";
import RatingService from "../services/user/RatingService";
import { Navigate } from "react-router-dom";
import Rating from "@material-ui/lab/Rating";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import { useSelector } from "react-redux";
import { loginSelector } from "../store/reducers/loginSlice";

const Container = styled.div``;

const Wrapper = styled.div`
  padding: 50px;
  display: flex;
  ${mobile({ padding: "10px", flexDirection: "column" })}
`;
const WrapperRating = styled.div`
  width: 10rem;
  display: flex;
  align-items: center;
  justify-content: center;
`;
const WrapperTotalRatingsText = styled.p`
  height: 1rem;
  margin-left: 0.5rem;
  font-size: 16px;
`;

const ImgContainer = styled.div`
  flex: 1;
`;

const Image = styled.img`
  height: 85vh;
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

const BookInfo = styled.p`
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

const RatingContainer = styled.div`
  width: 55%;
  align-items: center;
  justify-content: space-between;
  margin-top: 2rem;
  text-align: center;
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
  const [isUserRatedBook, setIsUserRatedBook] = useState(false);
  const [userRatingValue, setUserRatingValue] = useState(0);
  const [dataChange, toggleDataChange] = useState(false);
  const loginInfo = useSelector(loginSelector);
  let { id } = useParams();

  useEffect(() => {
    fetchData();
  }, [dataChange]);

  const fetchData = async () => {
    await setIsLoading(true);
    BookService.getBookById(id).then(async (res) => {
      if (res.status === 404) {
        await setIsBookExist(false);
      } else if (res.status === 200) {
        await setIsBookExist(true);
        await setBook(res.data);

        RatingService.getUserRatingPoingBook(id).then(async (ratingRes) => {
          if (ratingRes.status === 404) {
            alert(ratingRes.data.message);
          } else if (ratingRes.status === 200) {
            if (ratingRes.data === 0) {
              await setIsUserRatedBook(false);
            } else {
              setUserRatingValue(ratingRes.data);
              await setIsUserRatedBook(true);
            }
          }
        });
      }
      await setIsLoading(false);
    });
  };

  const formatter = new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "VND",
    minimumFractionDigits: 0,
  });

  const onRatingSubmit = (event, newRatingValue) => {
    event.preventDefault(event);

    const ratingRequest = JSON.stringify({
      bookId: book.id,
      userId: loginInfo.id,
      point: newRatingValue,
    });

    console.log(ratingRequest);
    RatingService.postRating(ratingRequest).then(async (ratingRes) => {
      if (ratingRes.status === 404) {
        alert(ratingRes.data.message);
      } else if (ratingRes.status === 200) {
        alert("Book successfully rated.");
        await toggleDataChange(!dataChange);
      }
    });
  };

  const getCategogiesString = () => {
    let CategogiesString = "";
    book.categories.map(
      (category) => (CategogiesString += category.name + " - ")
    );
    return CategogiesString.slice(0, -2);
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
            <WrapperRating>
              <Rating
                name="Rating Label"
                value={book.ratingPoint}
                disabled={true}
                precision={0.5}
              />
              <WrapperTotalRatingsText>
                <em>{book.totalRatings}</em>
              </WrapperTotalRatingsText>
            </WrapperRating>
            <BookInfo>{"Description:  " + book.description}</BookInfo>
            <BookInfo>{"Author:  " + book.author}</BookInfo>
            <BookInfo>{"Total pages:  " + book.totalPages}</BookInfo>
            <BookInfo>{"Required age:  " + book.requiredAge}</BookInfo>
            <BookInfo>{"Release date:  " + book.releaseDate}</BookInfo>
            <BookInfo>{"Categories:  " + getCategogiesString()}</BookInfo>
            <Price>{formatter.format(book.price) + " VND"}</Price>
            <AddContainer>
              <AmountContainer>
                <Remove />
                <Amount>1</Amount>
                <Add />
              </AmountContainer>
              <Button>ADD TO CART</Button>
            </AddContainer>
            <RatingContainer>
              <Box component="fieldset" mb={3} borderColor="transparent">
                <Typography component="legend">
                  {loginInfo.isLogged
                    ? isUserRatedBook
                      ? "Your rating for this book"
                      : "Please Rate our book!"
                    : "Please loggin to rate this book"}
                </Typography>
                <Rating
                  name="Rating book"
                  disabled={isUserRatedBook || !loginInfo.isLogged}
                  value={userRatingValue}
                  size="large"
                  onChange={(event, newRatingValue) => {
                    onRatingSubmit(event, newRatingValue);
                  }}
                />
              </Box>
            </RatingContainer>
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
