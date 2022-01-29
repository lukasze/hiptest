import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './glasses.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const GlassesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const glassesEntity = useAppSelector(state => state.glasses.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="glassesDetailsHeading">
          <Translate contentKey="hiptestApp.glasses.detail.title">Glasses</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{glassesEntity.id}</dd>
          <dt>
            <span id="model">
              <Translate contentKey="hiptestApp.glasses.model">Model</Translate>
            </span>
          </dt>
          <dd>{glassesEntity.model}</dd>
          <dt>
            <span id="front">
              <Translate contentKey="hiptestApp.glasses.front">Front</Translate>
            </span>
          </dt>
          <dd>{glassesEntity.front}</dd>
          <dt>
            <span id="temples">
              <Translate contentKey="hiptestApp.glasses.temples">Temples</Translate>
            </span>
          </dt>
          <dd>{glassesEntity.temples}</dd>
          <dt>
            <span id="lenses">
              <Translate contentKey="hiptestApp.glasses.lenses">Lenses</Translate>
            </span>
          </dt>
          <dd>{glassesEntity.lenses}</dd>
        </dl>
        <Button tag={Link} to="/glasses" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/glasses/${glassesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GlassesDetail;
