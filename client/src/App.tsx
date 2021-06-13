import React, { useState, useEffect } from 'react';
import './App.css';
import { Map } from './Map/Map';
import { HistoricalTable } from 'Table/HistoricalTable';
import { Button } from '@material-ui/core';
import { BusTrackingModal } from 'Modal/BusTrackingModal';
import { IFeedback, Feedback } from 'Feedback/Feedback';
import { makeStyles } from '@material-ui/core/styles';
import {
  FormControl,
  InputLabel,
  Select,
  SelectProps,
} from '@material-ui/core';

const busId = '00000000-0000-0000-0000-000000002481';

const useStyles = makeStyles((theme) => ({
  formControl: {
    minWidth: 130,
  },

  inputFeedback: {
    color: 'red',
    textAlign: 'start',
    fontSize: '13px',
    fontWeight: 400,
    marginTop: '3px',
  },
}));

function App() {
  // const { isLoaded, loadError } = useLoadScript({
  //   googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY ? ,
  //   libraries,
  // });
  const classes = useStyles();
  const [isBusTrackingModalOpen, setIsBusTrackingModalOpen] = useState<boolean>(
    false
  );

  let eventSource: EventSource | undefined = undefined;
  const [listening, setListening] = useState(false);
  const [feedbackData, setFeedbackData] = useState<IFeedback>();

  const [busIds, setBusIds] = useState<any[]>([]);
  const [currentBusId, setCurrentBusId] = useState<any>();
  useEffect(() => {
    fetch('https://localhost:8085/bus/ids')
      .then((res) => res.json())
      .then((result) => {
        console.log(result);
        setBusIds(result);
      });
  }, []);
  // const [currentPosition, setCurrentPosition] = useState<any>({});

  // useEffect(() => {
  //   navigator.geolocation.getCurrentPosition(success);
  // }, []);

  // const success = (position: any) => {
  //   const currentPosition = {
  //     lat: position.coords.latitude,
  //     lng: position.coords.longitude,
  //   };
  //   setCurrentPosition(currentPosition);
  // };

  // console.log(currentPosition);

  //-8.610423,41.14931 lat long
  const setBusAlarm = async () => {
    const response = await fetch(
      `http://localhost:8082/current-location?id=${currentBusId}&latLong=-8.610423,41.14931`
    );
    console.log(response);
  };

  useEffect(() => {
    if (!listening) {
      eventSource = new EventSource('http://localhost:8080/alarm');

      eventSource.onopen = (event) => {
        console.log('connection opened');
      };

      eventSource.onmessage = (event) => {
        console.log('result', event.data);
        setFeedbackData({
          open: true,
          severity: 'warning',
          message: event.data,
        });
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

  return (
    <div className='App' style={{ flexDirection: 'column' }}>
      {/*  {center={currentPosition}} */}
      {/* <div
        style={{
          padding: 50,
          width: '90%',
          marginBottom: 40,
        }}
      >
        <HistoricalTable />
      </div> */}
      <FormControl style={{ width: 150 }}>
        <InputLabel>Bus ids</InputLabel>
        <Select
          native
          value={currentBusId || ''}
          onChange={(event: any) => {
            setCurrentBusId(event.target.value);
          }}
        >
          <option aria-label='None' value='' />
          {busIds.map((item: any, index: number) => (
            <option key={index} value={item}>
              {item}
            </option>
          ))}
        </Select>
      </FormControl>
      <div style={{ position: 'absolute', top: '10%', left: '20' }}>
        <Button
          variant='contained'
          color='primary'
          onClick={() => setIsBusTrackingModalOpen(true)}
        >
          Track bus
        </Button>
      </div>
      <div style={{ position: 'absolute', top: '15%', left: '20' }}>
        <Button
          variant='contained'
          color='primary'
          onClick={() => setBusAlarm()}
        >
          Set alarm for bus
        </Button>
      </div>
      {isBusTrackingModalOpen && (
        <BusTrackingModal
          isOpen={isBusTrackingModalOpen}
          busId={currentBusId}
          closeModal={() => {
            setIsBusTrackingModalOpen(false);
          }}
        />
      )}
      {feedbackData?.open && feedbackData?.message && feedbackData?.severity && (
        <Feedback
          open={feedbackData?.open}
          message={feedbackData?.message}
          severity={feedbackData?.severity}
          onClose={() => {
            setFeedbackData(undefined);
          }}
        />
      )}
    </div>
  );
}

export default App;
