import * as React from "react";
import "./BookPage.css"

async function getBookCollection(){
    return fetch("http://localhost:5000/api/book-collection",
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(data => data.json())
}

export default class BookPage extends React.Component {
    constructor(props){
        super(props);

        this.state = {
            isDataLoaded: false,
        }
        this.books = [];
    }

    async componentDidMount(){
        this.books = await getBookCollection();
        console.log(this.books)
        this.setState({isDataLoaded: true})
    }

    render() {

        return (
            <div className="book_container">
                <div>
                    {
                        this.state.isDataLoaded ?
                            (<table className="table">
                                <thead className="table_head">
                                <tr>
                                    <th scope="col">Id</th>
                                    <th scope="col">Title</th>
                                    <th scope="col">Author</th>
                                    <th scope="col">Editor</th>
                                    <th scope="col">Genre</th>
                                    <th scope="col">Year</th>
                                    <th scope="col">Rezumat</th>
                                </tr>
                                </thead>
                                <tbody>
                                {
                                    this.books.map(function(book, i){
                                            return <tr>
                                                <th scope="row">{book.id}</th>
                                                <td>{book.bookTitle}</td>
                                                <td>{book.author}</td>
                                                <td>{book.editor}</td>
                                                <td>{book.bookGenre}</td>
                                                <td>{book.bookYear}</td>
                                                <td>{book.rezumat}</td>
                                            </tr>;
                                        })
                                }

                                </tbody>
                            </table>):
                            (<div>Books</div>)
                        }
                </div>
            </div>
        );
    }
}