import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Bike from './bike';
import BikeDetail from './bike-detail';
import BikeUpdate from './bike-update';
import BikeDeleteDialog from './bike-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BikeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BikeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BikeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Bike} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BikeDeleteDialog} />
  </>
);

export default Routes;
