import {Button} from 'react-bootstrap';
import * as React from 'react';
import {Link} from 'react-router-dom';


export default class LocationReportSubmit extends React.Component {

    render() {
        return (
            <div>
                <h1>Submission Successful</h1>
                <Link to='/'>
                    <Button type='submit'> Go back</Button>
                </Link>
            </div>
        );
    }
}