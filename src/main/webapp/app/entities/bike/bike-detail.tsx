import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bike.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BikeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bikeEntity = useAppSelector(state => state.bike.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bikeDetailsHeading">
          <Translate contentKey="hiptestApp.bike.detail.title">Bike</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bikeEntity.id}</dd>
          <dt>
            <span id="model">
              <Translate contentKey="hiptestApp.bike.model">Model</Translate>
            </span>
          </dt>
          <dd>{bikeEntity.model}</dd>
          <dt>
            <span id="serialNo">
              <Translate contentKey="hiptestApp.bike.serialNo">Serial No</Translate>
            </span>
          </dt>
          <dd>{bikeEntity.serialNo}</dd>
          <dt>
            <Translate contentKey="hiptestApp.bike.owner">Owner</Translate>
          </dt>
          <dd>{bikeEntity.owner ? bikeEntity.owner.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bike" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bike/${bikeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BikeDetail;
