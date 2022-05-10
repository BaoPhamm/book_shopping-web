import React from "react";
import Button from "@mui/material/Button";

const AddNewCategoryButton = ({ buttonRef, showModal }) => {
  return (
    <Button variant="contained" ref={buttonRef} onClick={showModal}>
      +ADD CATEGORY
    </Button>
  );
};
export default AddNewCategoryButton;
