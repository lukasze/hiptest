import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IDriver } from 'app/shared/model/driver.model';
import { getEntities as getDrivers } from 'app/entities/driver/driver.reducer';
import { getEntity, updateEntity, createEntity, reset } from './truck.reducer';
import { ITruck } from 'app/shared/model/truck.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TruckUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const drivers = useAppSelector(state => state.driver.entities);
  const truckEntity = useAppSelector(state => state.truck.entity);
  const loading = useAppSelector(state => state.truck.loading);
  const updating = useAppSelector(state => state.truck.updating);
  const updateSuccess = useAppSelector(state => state.truck.updateSuccess);
  const handleClose = () => {
    props.history.push('/truck');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getDrivers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...truckEntity,
      ...values,
      drivers: mapIdList(values.drivers),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...truckEntity,
          drivers: truckEntity?.drivers?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hiptestApp.truck.home.createOrEditLabel" data-cy="TruckCreateUpdateHeading">
            <Translate contentKey="hiptestApp.truck.home.createOrEditLabel">Create or edit a Truck</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="truck-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('hiptestApp.truck.model')} id="truck-model" name="model" data-cy="model" type="text" />
              <ValidatedField label={translate('hiptestApp.truck.engine')} id="truck-engine" name="engine" data-cy="engine" type="text" />
              <ValidatedField
                label={translate('hiptestApp.truck.serialNo')}
                id="truck-serialNo"
                name="serialNo"
                data-cy="serialNo"
                type="text"
              />
              <ValidatedField
                label={translate('hiptestApp.truck.driver')}
                id="truck-driver"
                data-cy="driver"
                type="select"
                multiple
                name="drivers"
              >
                <option value="" key="0" />
                {drivers
                  ? drivers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/truck" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TruckUpdate;
