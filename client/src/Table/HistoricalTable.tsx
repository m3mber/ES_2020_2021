import React, { useState, useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { DataGridExtended } from 'DataGrid/DataGridExtended';
import { GridColumns, GridRowsProp } from '@material-ui/data-grid';

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
    height: '500px',

    width: '100%',
  },
}));

const HistoricalTable: React.FC<IHistoricalTableProps> = ({}) => {
  const classes = useStyles();
  const [rows, setRows] = useState<GridRowsProp>([]);

  const columns: GridColumns = [
    {
      field: 'id',
      headerName: 'Id',
      headerAlign: 'center',
      align: 'center',
      flex: 1.5,
    },
    {
      field: 'code',
      headerName: 'Code',
      headerAlign: 'center',
      align: 'center',
      flex: 1.5,
    },
    {
      field: 'name',
      headerName: 'Name',
      headerAlign: 'center',
      align: 'center',
      flex: 2,
    },
  ];

  useEffect(() => {
    loadData();
  }, []);

  const loadData = () => {
    try {
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className={classes.rootDiv}>
      <DataGridExtended
        tableHeader={'Historical data'}
        containerClassName={classes.tableStyle}
        rows={rows}
        columns={columns}
        pageSize={25}
      />
    </div>
  );
};

export { HistoricalTable };
