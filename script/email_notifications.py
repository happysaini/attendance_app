# myapp.py
import logging
logging.basicConfig(level=logging.DEBUG)
def main():
    logging.info('Started this ')
    print("hello")
    logging.info('Finished')

if __name__ == '__main__':
    main()