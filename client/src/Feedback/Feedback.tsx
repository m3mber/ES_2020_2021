import React, { useState, useEffect } from 'react';
import { Snackbar, IconButton } from '@material-ui/core';
import { Alert } from '@material-ui/lab';
import HighlightOffIcon from '@material-ui/icons/HighlightOff';
import { makeStyles } from '@material-ui/core/styles';

export interface IFeedback {
  open: boolean;
  message: string;
  severity: 'success' | 'warning' | 'info' | 'error';
}

export interface IFeedbackProps {
  open: boolean;
  message: string;
  severity: 'success' | 'warning' | 'info' | 'error';
  onClose: Function;
}

const useStyles = makeStyles((theme) => ({
  close: {
    padding: theme.spacing(0.5),
  },
}));

const Feedback: React.FC<IFeedbackProps> = ({
  open,
  message,
  severity,
  onClose,
}) => {
  const [openFeedback, setOpenFeedback] = useState<boolean>();

  useEffect(() => {
    setOpenFeedback(open);
  }, [open]);

  const classes = useStyles();

  const handleClose = () => {
    setOpenFeedback(false);
    onClose();
  };

  return (
    <Snackbar
      open={openFeedback}
      anchorOrigin={{
        vertical: 'top',
        horizontal: 'center',
      }}
      autoHideDuration={10000}
      onClose={() => {
        onClose();
      }}
    >
      <div style={{ flexDirection: 'row', display: 'flex' }}>
        <Alert severity={severity}>{message}</Alert>
        <IconButton
          aria-label='close'
          className={classes.close}
          color='secondary'
          onClick={() => handleClose()}
        >
          <HighlightOffIcon color='secondary' />
        </IconButton>
      </div>
    </Snackbar>
  );
};

export { Feedback };
