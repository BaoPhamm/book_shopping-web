import React from "react";
import Button from "@mui/material/Button";

const AddCatToBookButton = ({ buttonRef, showModal }) => {
  return (
    <Button
      variant="contained"
      color="success"
      ref={buttonRef}
      onClick={showModal}
    >
      +CAT
    </Button>
  );
};
export default AddCatToBookButton;
