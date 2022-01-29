import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './truck.reducer';
import { ITruck } from 'app/shared/model/truck.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Truck = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const truckList = useAppSelector(state => state.truck.entities);
  const loading = useAppSelector(state => state.truck.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="truck-heading" data-cy="TruckHeading">
        <Translate contentKey="hiptestApp.truck.home.title">Trucks</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hiptestApp.truck.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hiptestApp.truck.home.createLabel">Create new Truck</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {truckList && truckList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hiptestApp.truck.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hiptestApp.truck.model">Model</Translate>
                </th>
                <th>
                  <Translate contentKey="hiptestApp.truck.engine">Engine</Translate>
                </th>
                <th>
                  <Translate contentKey="hiptestApp.truck.serialNo">Serial No</Translate>
                </th>
                <th>
                  <Translate contentKey="hiptestApp.truck.driver">Driver</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {truckList.map((truck, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${truck.id}`} color="link" size="sm">
                      {truck.id}
                    </Button>
                  </td>
                  <td>{truck.model}</td>
                  <td>{truck.engine}</td>
                  <td>{truck.serialNo}</td>
                  <td>
                    {truck.drivers
                      ? truck.drivers.map((val, j) => (
                          <span key={j}>
                            <Link to={`driver/${val.id}`}>{val.id}</Link>
                            {j === truck.drivers.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${truck.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${truck.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${truck.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hiptestApp.truck.home.notFound">No Trucks found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Truck;
