import React from "react";
import Button from "@mui/material/Button";

const AddBookButton = ({ buttonRef, showModal }) => {
  return (
    <Button variant="contained" ref={buttonRef} onClick={showModal}>
      +Addbook
    </Button>
  );
};
export default AddBookButton;
