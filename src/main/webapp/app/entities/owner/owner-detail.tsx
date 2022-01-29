import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './owner.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OwnerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ownerEntity = useAppSelector(state => state.owner.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ownerDetailsHeading">
          <Translate contentKey="hiptestApp.owner.detail.title">Owner</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ownerEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hiptestApp.owner.name">Name</Translate>
            </span>
          </dt>
          <dd>{ownerEntity.name}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="hiptestApp.owner.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{ownerEntity.lastName}</dd>
          <dt>
            <span id="mileage">
              <Translate contentKey="hiptestApp.owner.mileage">Mileage</Translate>
            </span>
          </dt>
          <dd>{ownerEntity.mileage}</dd>
        </dl>
        <Button tag={Link} to="/owner" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/owner/${ownerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OwnerDetail;
