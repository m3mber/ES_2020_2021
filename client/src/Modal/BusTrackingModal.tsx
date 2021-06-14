import React, { useState, useEffect } from 'react';
import { Typography, Modal, IconButton, makeStyles } from '@material-ui/core';
import HighlightOffIcon from '@material-ui/icons/HighlightOff';
import { Map } from '../Map/Map';

export interface IBusTrackingModalProps {
  isOpen: boolean;
  closeModal: Function;
  busId: string;
}

export interface IMessageData {
  id: number;
  node_id: number;
  location_id: number;
  head: string;
  lon: number;
  lat: number;
  speed: number;
  ts: string;
  write_time: string;
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
    width: '80%',
    height: '80%',
    borderRadius: '10px',
    flexDirection: 'column',
    justifyContent: 'start',
    alignItems: 'center',
    padding: 5,
  },
  exitButton: {
    position: 'absolute',
    right: '7%',
    top: '7%',
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

const BusTrackingModal: React.FC<IBusTrackingModalProps> = ({
  isOpen,
  closeModal,
  busId,
}) => {
  const classes = useStyles();
  // let places = [
  //   { latitude: 41.166058, longitude: -8.58294 },
  //   { latitude: 41.17144, longitude: -8.594005 },
  //   // { latitude: 41.172817, longitude: -8.607225 },
  //   // { latitude: 41.154613, longitude: -8.607225 },
  // ];
  let eventSource: EventSource | undefined = undefined;
  const [listening, setListening] = useState(false);
  const [places, setPlaces] = useState<
    { latitude: number; longitude: number }[]
  >([
    // { latitude: 41.166058, longitude: -8.58294 },
    // { latitude: 41.17144, longitude: -8.594005 },
  ]);

  console.log(places);
  useEffect(() => {
    getBusStops();
    if (!listening) {
      eventSource = new EventSource(
        `http://localhost:8080/location?id=${busId}`
      );

      eventSource.onopen = (event) => {
        console.log('connection opened');
      };

      eventSource.onmessage = (event) => {
        console.log('result', event.data);
        let message = event.data;
        let messageArray = message.split(','); //on index 0 lat, on index 1 long

        setPlaces((old) => [
          ...old,
          { latitude: parseFloat(messageArray[0]), longitude: parseFloat(messageArray[1]) },
        ]);
      };

      eventSource.onerror = (event: any) => {
        if (eventSource && event.target) {
          console.log(event.target.readyState);
          if (event.target.readyState === EventSource.CLOSED) {
            console.log('eventsource closed (' + event.target.readyState + ')');
          }
          eventSource.close();
        }
      };

      setListening(true);
    }
    return () => {
      eventSource && eventSource.close();
      console.log('eventsource closed');
    };
  }, []);

  const getBusStops = async () => {
    const databaseResponse = await fetch(
      `http://localhost:8084/bus/locations?busId=${busId}`
    );
    const body = await databaseResponse.json();
    console.log("Reponse from bus/locations");
    console.log(databaseResponse);
    console.log(body);
    for (let i = 0; i < body.length; i = i + 2) {
      setPlaces((old) => [
        ...old,
        { latitude: parseFloat(body[i]), longitude: parseFloat(body[i + 1]) },
      ]);
    }
  };

  return (
    <Modal
      disableBackdropClick={true}
      className={classes.modalStyle}
      open={isOpen}
    >
      <div className={classes.modalDiv}>
        <Map markers={places} />
        {/* {data.length > 0 &&
          data.map((item: any, index: number) => (
            <Typography key={index}>{item}</Typography>
          ))} */}
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

export { BusTrackingModal };
