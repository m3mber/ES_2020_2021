import React, { useEffect, useState } from 'react';
import { DataGridProps, DataGrid, GridRowModel } from '@material-ui/data-grid';
import { makeStyles, Typography } from '@material-ui/core';

const useStyles = makeStyles(() => ({
  selectedItemsCountContainer: {
    widht: '100%',
    height: '50px',
    marginTop: '10px',
    justifyContent: 'center',
    backgroundColor: '#FEF5D9',
  },
  defaultTableContainer: {
    height: '600px',
    width: '100%',
    display: 'flex',
    marginBottom: '30px',
    flexDirection: 'column',
  },
}));

export interface IGridRowModelExtended extends GridRowModel {
  isSelected: boolean;
}

export interface IDataGridExtendedProps extends DataGridProps {
  containerClassName?: string;
  tableHeader: string;
}

const DataGridExtended: React.FC<IDataGridExtendedProps> = ({
  containerClassName,
  tableHeader,
  ...rest
}) => {
  const [rows, setRows] = useState<IGridRowModelExtended[]>(
    rest.rows as IGridRowModelExtended[]
  );

  const classes = useStyles();

  useEffect(() => {
    setRows(rest.rows as IGridRowModelExtended[]);
  }, [rest.rows]);

  let columns = [...rest.columns];

  return (
    <div style={{ height: '100%', width: '100%' }}>
      <Typography
        variant='h6'
        style={{
          justifyContent: 'center',
          marginTop: '20px',
          marginBottom: '10px',
          width: '100%',
          textAlign: 'center',
        }}
      >
        {tableHeader}
      </Typography>

      <div
        className={
          containerClassName
            ? containerClassName
            : classes.defaultTableContainer
        }
      >
        <DataGrid
          {...rest}
          rows={rows}
          columns={columns}
          pageSize={25}
          rowsPerPageOptions={[25, 50, 100]}
          pagination
        />
      </div>
    </div>
  );
};

export { DataGridExtended };
