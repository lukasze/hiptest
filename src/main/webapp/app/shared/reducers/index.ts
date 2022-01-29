import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
import sessions from 'app/modules/account/sessions/sessions.reducer';
// prettier-ignore
import person from 'app/entities/person/person.reducer';
// prettier-ignore
import glasses from 'app/entities/glasses/glasses.reducer';
// prettier-ignore
import owner from 'app/entities/owner/owner.reducer';
// prettier-ignore
import bike from 'app/entities/bike/bike.reducer';
// prettier-ignore
import driver from 'app/entities/driver/driver.reducer';
// prettier-ignore
import truck from 'app/entities/truck/truck.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  sessions,
  person,
  glasses,
  owner,
  bike,
  driver,
  truck,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
