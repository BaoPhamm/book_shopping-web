import React, { Component } from "react";
import { Modal } from "../Modal";
import ChangPasswordButton from "../Forms/bookAdmin/buttons/ChangPasswordButton";
import AddBookButton from "../Forms/bookAdmin/buttons/AddBookButton";
import UpdateBookButton from "../Forms/bookAdmin/buttons/UpdateBookButton";
import AddCatToBookButton from "../Forms/bookAdmin/buttons/AddCatToBookButton";
import RemoveCatFromBookButton from "../Forms/bookAdmin/buttons/RemoveCatFromBookButton";
import DeleteBookButton from "../Forms/bookAdmin/buttons/DeleteBookButton";
import "./index.css";

export class PopupContainer extends Component {
  state = { isShown: false };
  showModal = () => {
    this.setState({ isShown: true }, () => {
      this.closeButton.focus();
    });
    this.toggleScrollLock();
  };
  closeModal = () => {
    this.setState({ isShown: false });
    this.TriggerButton.focus();
    this.toggleScrollLock();
  };
  onKeyDown = (event) => {
    if (event.keyCode === 27) {
      this.closeModal();
    }
  };

  toggleScrollLock = () => {
    document.querySelector("html").classList.toggle("scroll-lock");
  };
  GetButton = () => {
    if (this.props.typeSubmit === "changePassword") {
      return (
        <ChangPasswordButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    } else if (this.props.typeSubmit === "addBook") {
      return (
        <AddBookButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    } else if (this.props.typeSubmit === "updateBook") {
      return (
        <UpdateBookButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    } else if (this.props.typeSubmit === "addCatToBook") {
      return (
        <AddCatToBookButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    } else if (this.props.typeSubmit === "removeCatFromBook") {
      return (
        <RemoveCatFromBookButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    } else if (this.props.typeSubmit === "deleteBook") {
      return (
        <DeleteBookButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    }
  };

  render() {
    return (
      <React.Fragment>
        {this.GetButton()}
        {this.state.isShown ? (
          <Modal
            onSubmit={this.props.onSubmit}
            modalRef={(n) => (this.modal = n)}
            buttonRef={(n) => (this.closeButton = n)}
            closeModal={this.closeModal}
            onKeyDown={this.onKeyDown}
            typeSubmit={this.props.typeSubmit}
            productDetails={this.props.productDetails}
            allCategories={this.props.allCategories}
          />
        ) : null}
      </React.Fragment>
    );
  }
}

export default PopupContainer;
