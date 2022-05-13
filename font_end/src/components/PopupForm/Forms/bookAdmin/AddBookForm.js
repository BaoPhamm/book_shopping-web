import React, { useState } from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  width: 100%;
  background-color: white;
  text-align: center;
`;

const RowWrapper = styled.div`
  justify-content: start;
  width: 100%;
  display: grid;
  grid-template-columns: 14% 86%;
  align-items: center;
  margin-top: 2px;
`;

const Text = styled.p`
  font-size: 15px;
  text-align: left;
`;

const Input = styled.input`
  font-size: 14px;
  min-width: 80%;
  padding: 7px;
  right: 0;
`;
const InputDescription = styled.textarea`
  font-size: 14px;
  font-family: "Arial";
  min-width: 80%;
  height: 3.2rem;
  overflow-wrap: break-word;
  padding: 7px;
  right: 0;
`;
const Title = styled.h1`
  font-size: 24px;
  font-weight: 300;
`;
const Button = styled.button`
  width: 30%;
  border: none;
  padding: 15px 20px;
  background-color: teal;
  color: white;
  cursor: pointer;
  margin-bottom: 1rem;
  margin-top: 1rem;
`;

const AddBookForm = ({ onSubmit }) => {
  const [bookImageURL, setBookImageURL] = useState("");

  const onChangeBookImg = async (event) => {
    await setBookImageURL(URL.createObjectURL(event.target.files[0]));
  };
  return (
    <Wrapper>
      <Title>ADD NEW BOOK</Title>
      <form onSubmit={onSubmit}>
        <RowWrapper>
          <Text>Title:</Text>
          <Input type="text" name="title" placeholder="Title" />
        </RowWrapper>
        <RowWrapper>
          <Text>Author:</Text>
          <Input type="text" name="author" placeholder="Author" />
        </RowWrapper>
        <RowWrapper>
          <Text>Total pages:</Text>
          <Input type="number" name="totalPages" placeholder="Total pages" />
        </RowWrapper>
        <RowWrapper>
          <Text>Required age:</Text>
          <Input type="number" name="requiredAge" placeholder="Required age" />
        </RowWrapper>
        <RowWrapper>
          <Text>Release date:</Text>
          <Input type="text" name="releaseDate" placeholder="Release date" />
        </RowWrapper>
        <RowWrapper>
          <Text>Price:</Text>
          <Input type="number" name="price" placeholder="Price" />
        </RowWrapper>
        <RowWrapper>
          <Text>Description:</Text>
          <InputDescription
            type="text"
            name="description"
            placeholder="Description"
          />
        </RowWrapper>
        <RowWrapper>
          <Text>Book image:</Text>
          <Input type="file" name="imgFile" onChange={onChangeBookImg} />
          <img
            src={bookImageURL}
            width="200"
            alt="https://firebasestorage.googleapis.com/v0/b/shopping-web-a0eb0.appspot.com/o/category_img%2Fdefault.jpg"
          />
        </RowWrapper>
        <Button>ADD</Button>
      </form>
    </Wrapper>
  );
};
export default AddBookForm;
