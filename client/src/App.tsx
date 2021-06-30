import React, { useState, useEffect } from 'react';
import './App.css';
import { HistoricalTable } from 'Table/HistoricalTable';
import { IFeedback, Feedback } from 'Feedback/Feedback';
import { makeStyles } from '@material-ui/core/styles';
import { AppBar, Typography, Toolbar } from '@material-ui/core';

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
  const [feedbackData, setFeedbackData] = useState<IFeedback>();

  let eventSource: EventSource | undefined = undefined;
  const [listening, setListening] = useState(false);

  useEffect(() => {
    if (!listening) {
      eventSource = new EventSource('http://localhost:13001/alarm');

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
      <AppBar position='static' style={{ height: 60 }}>
        <Toolbar style={{ paddingRight: 24 }}>
          <Typography
            component='h1'
            variant='h6'
            color='inherit'
            noWrap
            style={{ flexGrow: 1 }}
          >
            BitBusTracker
          </Typography>
        </Toolbar>
      </AppBar>
      <div
        style={{
          padding: 50,
          marginBottom: 40,
        }}
      >
        <HistoricalTable />
      </div>
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
