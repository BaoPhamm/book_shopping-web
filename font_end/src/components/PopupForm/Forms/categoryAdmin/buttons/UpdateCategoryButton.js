import React from "react";
import Button from "@mui/material/Button";

const UpdateCategoryButton = ({ buttonRef, showModal }) => {
  return (
    <Button
      variant="contained"
      color="success"
      ref={buttonRef}
      onClick={showModal}
    >
      UPDATE
    </Button>
  );
};
export default UpdateCategoryButton;
