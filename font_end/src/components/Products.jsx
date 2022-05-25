import React, { useState, useEffect } from "react";
import styled from "styled-components";
import Product from "./Product";
import BookService from "../services/user/BookService";
import Pagination from "@mui/material/Pagination";
import Stack from "@mui/material/Stack";

const Container = styled.div`
  padding: 8px;
  display: flex;
  flex-direction: column;
`;

const ProductContainer = styled.div`
  display: grid;
  grid-template-columns: 19vw 19vw 19vw 19vw 19vw;
  width: 100%;
  justify-content: start;
  min-height: 400px;
`;

const PagingBarContainer = styled.div`
  margin: 1rem;
  display: flex;
  justify-content: center;
`;

const Products = (props) => {
  const [allProducts, setAllProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [dataChange, toggleDataChange] = useState(false);
  const [pageNumber, setPageNumber] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const productsPerPage = 20;

  const GetTotalPages = (totalPro) => {
    return totalPro % productsPerPage === 0
      ? Math.floor(totalPro / productsPerPage)
      : Math.floor(totalPro / productsPerPage) + 1;
  };

  useEffect(() => {
    GetAllBooks();
  }, [props.selectedCategory, dataChange]);

  const GetAllBooks = async () => {
    await setIsLoading(true);
    if (Number(props.selectedCategory) === 0) {
      BookService.getAllBooks(pageNumber).then(async (res) => {
        await setAllProducts([...res]);
      });
      GetTotalPtoducts();
    } else {
      BookService.getBooksByCategory(props.selectedCategory).then(
        async (res) => {
          await setAllProducts([...res]);
        }
      );
      getTotalBooksByCategory(props.selectedCategory);
    }
  };

  const GetTotalPtoducts = async () => {
    BookService.getTotalBooks().then(async (res) => {
      setTotalPages(GetTotalPages(res.data));
      await setIsLoading(false);
    });
  };

  const getTotalBooksByCategory = async (categoryId) => {
    BookService.getTotalBooksByCategory(categoryId).then(async (res) => {
      setTotalPages(GetTotalPages(res.data));
      await setIsLoading(false);
    });
  };

  const handleChangePage = async (event, newPageNumber) => {
    await setPageNumber(newPageNumber - 1);
    await toggleDataChange(!dataChange);
  };

  return (
    <Container>
      <ProductContainer>
        {!isLoading
          ? allProducts.map((item) => (
              <div>
                <Product productItem={item} key={item.id} />
              </div>
            ))
          : ""}
      </ProductContainer>
      <PagingBarContainer>
        <Stack>
          <Pagination
            count={totalPages}
            showFirstButton
            showLastButton
            defaultPage={1}
            page={pageNumber + 1}
            onChange={handleChangePage}
          />
        </Stack>
      </PagingBarContainer>
    </Container>
  );
};

export default Products;
