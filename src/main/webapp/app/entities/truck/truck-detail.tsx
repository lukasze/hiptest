import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './truck.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TruckDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const truckEntity = useAppSelector(state => state.truck.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="truckDetailsHeading">
          <Translate contentKey="hiptestApp.truck.detail.title">Truck</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{truckEntity.id}</dd>
          <dt>
            <span id="model">
              <Translate contentKey="hiptestApp.truck.model">Model</Translate>
            </span>
          </dt>
          <dd>{truckEntity.model}</dd>
          <dt>
            <span id="engine">
              <Translate contentKey="hiptestApp.truck.engine">Engine</Translate>
            </span>
          </dt>
          <dd>{truckEntity.engine}</dd>
          <dt>
            <span id="serialNo">
              <Translate contentKey="hiptestApp.truck.serialNo">Serial No</Translate>
            </span>
          </dt>
          <dd>{truckEntity.serialNo}</dd>
          <dt>
            <Translate contentKey="hiptestApp.truck.driver">Driver</Translate>
          </dt>
          <dd>
            {truckEntity.drivers
              ? truckEntity.drivers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {truckEntity.drivers && i === truckEntity.drivers.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/truck" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/truck/${truckEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TruckDetail;
