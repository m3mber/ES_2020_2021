import React, { useState, useEffect } from 'react';
import { Typography, Modal, IconButton, makeStyles } from '@material-ui/core';
import HighlightOffIcon from '@material-ui/icons/HighlightOff';

export interface IBusAlarmModalProps {
  isOpen: boolean;
  closeModal: Function;
  busId: string;
}

const useStyles = makeStyles((theme) => ({
  modalStyle: {
    justifyContent: 'center',
    alignItems: 'center',
    display: 'flex',
  },
  modalDiv: {
    overflow: 'auto',
    backgroundColor: 'white',
    width: '30%',
    height: '30%',
    borderRadius: '10px',
    flexDirection: 'column',
    justifyContent: 'start',
    alignItems: 'center',
    padding: 5,
  },
  exitButton: {
    position: 'absolute',
    right: '33%',
    top: '33%',
    backgroundColor: 'white',
    '&:hover': {
      background: 'white',
      transform: 'scale(1.15)',
    },
  },
  loadingLanguages: {
    position: 'absolute',
    width: '30%',
    height: '30%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: '10px',
  },
}));

const BusAlarmModal: React.FC<IBusAlarmModalProps> = ({
  isOpen,
  closeModal,
  busId,
}) => {
  const classes = useStyles();

  let eventSource: EventSource | undefined = undefined;
  const [listening, setListening] = useState(false);
  const [data, setData] = useState<string[]>([]);
  useEffect(() => {
    // if (!listening) {
    //   eventSource = new EventSource(`http://localhost:8080/time?id=${busId}`);
    //   eventSource.onopen = (event) => {
    //     console.log('connection opened');
    //   };
    //   eventSource.onmessage = (event) => {
    //     console.log('result', event.data);
    //     setData((old) => [...old, event.data]);
    //   };
    //   eventSource.onerror = (event: any) => {
    //     if (eventSource && event.target) {
    //       console.log(event.target.readyState);
    //       if (event.target.readyState === EventSource.CLOSED) {
    //         console.log('eventsource closed (' + event.target.readyState + ')');
    //       }
    //       eventSource.close();
    //     }
    //   };
    //   setListening(true);
    // }
    // return () => {
    //   eventSource && eventSource.close();
    //   console.log('eventsource closed');
    // };
  }, []);

  return (
    <Modal
      disableBackdropClick={true}
      className={classes.modalStyle}
      open={isOpen}
    >
      <div className={classes.modalDiv}>
        {data.length > 0 &&
          data.map((item: any) => <Typography>{item}</Typography>)}
        <IconButton
          className={classes.exitButton}
          onClick={() => {
            closeModal();
          }}
        >
          <HighlightOffIcon fontSize='large' color='secondary' />
        </IconButton>
      </div>
    </Modal>
  );
};

export { BusAlarmModal };
