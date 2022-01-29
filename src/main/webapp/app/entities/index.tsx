import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Person from './person';
import Glasses from './glasses';
import Owner from './owner';
import Bike from './bike';
import Driver from './driver';
import Truck from './truck';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}person`} component={Person} />
      <ErrorBoundaryRoute path={`${match.url}glasses`} component={Glasses} />
      <ErrorBoundaryRoute path={`${match.url}owner`} component={Owner} />
      <ErrorBoundaryRoute path={`${match.url}bike`} component={Bike} />
      <ErrorBoundaryRoute path={`${match.url}driver`} component={Driver} />
      <ErrorBoundaryRoute path={`${match.url}truck`} component={Truck} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
