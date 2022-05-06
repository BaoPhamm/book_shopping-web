import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { mobile } from "../responsive";
import CategoryItem from "./CategoryItem";
import CategoryService from "../services/user/CategoryService";

const Container = styled.div`
  display: flex;
  padding: 10px;
  width: 96vw;
  justify-content: space-between;
  ${mobile({ padding: "0px", flexDirection: "column" })}
`;

const Categories = () => {
  const [allCategories, setAllCategories] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    GetAllCategories();
  }, []);

  const GetAllCategories = async () => {
    await setIsLoading(true);
    CategoryService.getAllCategories().then(async (res) => {
      await setAllCategories([...res]);
      await setIsLoading(false);
    });
  };

  return !isLoading ? (
    <Container>
      {allCategories.map((item) => (
        <CategoryItem item={item} key={item.id} />
      ))}
    </Container>
  ) : (
    ""
  );
};

export default Categories;
