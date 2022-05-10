import React from "react";
import Button from "@mui/material/Button";

const DeleteBookButton = ({ buttonRef, showModal }) => {
  return (
    <Button
      variant="contained"
      color="error"
      ref={buttonRef}
      onClick={showModal}
    >
      DELETE
    </Button>
  );
};
export default DeleteBookButton;
