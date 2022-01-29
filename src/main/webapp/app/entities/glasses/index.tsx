import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Glasses from './glasses';
import GlassesDetail from './glasses-detail';
import GlassesUpdate from './glasses-update';
import GlassesDeleteDialog from './glasses-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GlassesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GlassesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GlassesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Glasses} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GlassesDeleteDialog} />
  </>
);

export default Routes;
