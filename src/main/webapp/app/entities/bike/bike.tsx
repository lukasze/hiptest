import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './bike.reducer';
import { IBike } from 'app/shared/model/bike.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Bike = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const bikeList = useAppSelector(state => state.bike.entities);
  const loading = useAppSelector(state => state.bike.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="bike-heading" data-cy="BikeHeading">
        <Translate contentKey="hiptestApp.bike.home.title">Bikes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hiptestApp.bike.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hiptestApp.bike.home.createLabel">Create new Bike</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bikeList && bikeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hiptestApp.bike.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hiptestApp.bike.model">Model</Translate>
                </th>
                <th>
                  <Translate contentKey="hiptestApp.bike.serialNo">Serial No</Translate>
                </th>
                <th>
                  <Translate contentKey="hiptestApp.bike.owner">Owner</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bikeList.map((bike, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${bike.id}`} color="link" size="sm">
                      {bike.id}
                    </Button>
                  </td>
                  <td>{bike.model}</td>
                  <td>{bike.serialNo}</td>
                  <td>{bike.owner ? <Link to={`owner/${bike.owner.id}`}>{bike.owner.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bike.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bike.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bike.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="hiptestApp.bike.home.notFound">No Bikes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Bike;
