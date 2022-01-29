import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Truck from './truck';
import TruckDetail from './truck-detail';
import TruckUpdate from './truck-update';
import TruckDeleteDialog from './truck-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TruckUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TruckUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TruckDetail} />
      <ErrorBoundaryRoute path={match.url} component={Truck} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TruckDeleteDialog} />
  </>
);

export default Routes;
