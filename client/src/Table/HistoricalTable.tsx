import React, { useState, useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { DataGridExtended } from 'DataGrid/DataGridExtended';
import {
  GridColumns,
  GridRowsProp,
  GridColDef,
  GridCellParams,
} from '@material-ui/data-grid';
import { IconButton } from '@material-ui/core';
import TrackChangesIcon from '@material-ui/icons/TrackChanges';
import { BusTrackingModal } from 'Modal/BusTrackingModal';
import AlarmIcon from '@material-ui/icons/Alarm';
import { IFeedback, Feedback } from 'Feedback/Feedback';

export interface IHistoricalTableProps {}

const useStyles = makeStyles((theme) => ({
  rootDiv: {
    flexDirection: 'column',
    display: 'flex',
    height: '400px',
  },
  textField: {
    textAlign: 'left',
    width: 'auto',
    margin: 15,
    height: 40,
  },
  tableStyle: {
    display: 'flex',
    height: '600px',
    width: '100%',
  },
  actionButton: {
    marginLeft: 4,
  },
}));

const HistoricalTable: React.FC<IHistoricalTableProps> = ({}) => {
  const classes = useStyles();
  const [rows, setRows] = useState<GridRowsProp>([]);

  const [isBusTrackingModalOpen, setIsBusTrackingModalOpen] = useState<boolean>(
    false
  );
  const [currentBusId, setCurrentBusId] = useState<any>();
  const [feedbackData, setFeedbackData] = useState<IFeedback>();

  const columns: GridColumns = [
    {
      field: 'id',
      headerName: 'Id',
      headerAlign: 'center',
      align: 'center',
      flex: 1.5,
    },
    {
      field: 'track',
      headerName: 'Track',
      flex: 1.5,
      headerAlign: 'center',
      align: 'center',
      disableClickEventBubbling: true,
      renderCell: (param: GridCellParams) => {
        return (
          <IconButton
            className={classes.actionButton}
            onClick={() => {
              setCurrentBusId(param.row.id);
              setIsBusTrackingModalOpen(true);
            }}
          >
            <TrackChangesIcon
              style={{
                color: '#48D14D',
              }}
            />
          </IconButton>
        );
      },
    },
    {
      field: 'alarm',
      headerName: 'Alarm',
      flex: 1.5,
      headerAlign: 'center',
      align: 'center',
      disableClickEventBubbling: true,
      renderCell: (param: GridCellParams) => {
        return (
          <IconButton
            className={classes.actionButton}
            onClick={() => {
              setBusAlarm(param.row.id);
            }}
          >
            <AlarmIcon
              style={{
                color: '#EF2D34',
              }}
            />
          </IconButton>
        );
      },
    },
  ];

  const setBusAlarm = async (currentBusId: number) => {
    await fetch(
      `http://localhost:8082/current-location?id=${currentBusId}&latLong=-8.610423,41.14931`
    );
    setFeedbackData({
      open: true,
      message: `Alarm is setted for bus ${currentBusId}`,
      severity: 'info',
    });
  };

  useEffect(() => {
    loadData();
  }, []);

  const loadData = () => {
    try {
      fetch('http://localhost:8084/bus/ids')
        .then((res) => res.json())
        .then((result) => {
          let response = result.map((item: any) => {
            return { id: item };
          });
          setRows(response);
        });
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className={classes.rootDiv}>
      <DataGridExtended
        tableHeader={'Bus data'}
        containerClassName={classes.tableStyle}
        rows={rows}
        columns={columns}
        pageSize={25}
      />
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
};

export { HistoricalTable };
