import * as React from 'react';
import {Button, Panel, Modal} from 'react-bootstrap';
import {apiGet} from '../utilities/request-helper';
import {errorLogAndRedirect} from '../error';

export default class SearchResult extends React.Component {
    constructor()
    {
        super();
        this.state={
            show:false
        };
    }
    handleModal()
    {
        this.setState({show:!this.state.show});
    }


    render() {
        return (
            <div className='results'>
                {this.getResultsHeader(this.props.results)}
                {this.renderResults(this.props.results)}
            </div>
        );
    }

    renderResults(results) {
        return results.map((result, index) => {
            return (
                <Panel id="resultsBox" key={index}>
                    <Panel.Heading>Result</Panel.Heading>
                    <Panel.Body>
                        {this.renderResultBody(result)}
                        <Button bsStyle="success" type="button" onClick={() => this.generatePdf(result[Object.keys(result)[0]])}>Export to PDF</Button>
                        <Button bsStyle="success" type="button" onClick={() =>this.handleModal()}>Open Modal</Button>
                        <Modal show={this.state.show} >
                            <Modal.Header>Modal Head Part</Modal.Header>
                            <Modal.Body>This is the modal body</Modal.Body>
                            <Modal.Footer>
                                <Button bsStyle="success" type="button" onClick={() =>this.handleModal()}>
                            Close modal
                                </Button>
                            </Modal.Footer>
                        </Modal>
                    </Panel.Body>
                </Panel>
            );
        });
    }

    generatePdf(id) {
        apiGet('reports/locationstatuses/pdf', id)
            .then(results => console.log(results))
            .catch(errorLogAndRedirect);
    }

    renderResultBody(result) {
        return Object.keys(result).map(key => {
            return <p key={key} id={key}>{`${key}: ${result[key]}`}</p>;
        });
    }

    getResultsHeader(results) {
        return results.length > 0
            ? (results.length === 1
                ? <h3>{`${results.length} result`}</h3>
                : <h3>{`${results.length} results`}</h3>)
            : '';
    }
}
